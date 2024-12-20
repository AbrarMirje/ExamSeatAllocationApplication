import { Component, OnInit } from '@angular/core';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-hall-ticket',
  templateUrl: './hall-ticket.component.html',
  styleUrls: ['./hall-ticket.component.css'],
})
export class HallTicketComponent implements OnInit {
  studentData: any;
  positionDto = {
    blockId: 0,
    seatNumber: '',
  };
  highlightedSeatIndex: number | null = null;

  // Generate seat rows dynamically
  seatRows = [
    Array.from({ length: 10 }, (_, i) => ({ seatNumber: i + 1, index: i })),
    Array.from({ length: 10 }, (_, i) => ({
      seatNumber: i + 11,
      index: i + 10,
    })),
    Array.from({ length: 10 }, (_, i) => ({
      seatNumber: i + 21,
      index: i + 20,
    })),
  ];

  constructor(private admin: AdminService) {}

  ngOnInit(): void {
    this.studentData = this.admin.getStudentData();

    // Set positionDto values
    this.positionDto.blockId = this.studentData.blockId;
    this.positionDto.seatNumber = this.studentData.seatNumber;

    // Fetch the highlighted seat index
    this.getStudentPosition();
  }

  // Fetch the seat position index from the backend
  getStudentPosition(): void {
    this.admin.getStudentPosition(this.positionDto).subscribe(
      (response: number) => {
        console.log('response: ', response);
        this.highlightedSeatIndex = response; // Highlight the returned index
      },
      (error) => {
        console.error('Error fetching student position:', error);
      }
    );
  }

  // Method to download the page as a PDF
  downloadPagePDF(): void {
    const container = document.querySelector('.container') as HTMLElement;

    if (!container) {
      console.error('Container element not found!');
      return;
    }

    setTimeout(() => {
      html2canvas(container, {
        useCORS: true,
        logging: true,
        scale: 2,
        scrollX: 0,
        scrollY: -window.scrollY,
      })
        .then((canvas) => {
          const imgData = canvas.toDataURL('image/png');
          const pdf = new jsPDF('p', 'mm', 'a4');
          const imgWidth = 190;
          const pageHeight = 297;
          const imgHeight = (canvas.height * imgWidth) / canvas.width;

          let heightLeft = imgHeight;
          let position = 0;

          pdf.addImage(imgData, 'PNG', 10, position, imgWidth, imgHeight);
          heightLeft -= pageHeight;

          while (heightLeft > 0) {
            position = heightLeft - imgHeight;
            pdf.addPage();
            pdf.addImage(imgData, 'PNG', 10, position, imgWidth, imgHeight);
            heightLeft -= pageHeight;
          }

          pdf.save('page.pdf');
        })
        .catch((err) => {
          console.error('Error generating PDF:', err);
        });
    }, 300);
  }
}
