import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  constructor(private readonly service: AdminService) {}

  blocks: any[] = [];
  seatAllocation: any[] = [];
  isAllocated = false;

  ngOnInit(): void {
    this.fetchBlocks();
  }

  // Fetch blocks from the server
  fetchBlocks() {
    this.service.getAllBlocks().subscribe((res) => {
      if (res !== null) {
        this.blocks = res;
      }
    });
    this.checkRemainingSeats();
  }

  checkRemainingSeats() {
    this.blocks.map((s) => {
      this.seatAllocation = s.seatAllocations;
      if (this.seatAllocation.length == 0) {
        this.isAllocated = false;
      } else {
        this.isAllocated = true;
      }
    });
  }

  student = {
    blockId: 0,
    seatNumber: 0,
  };

  studentInfo = {
    firstName: '',
    lastName: '',
    prnNumber: '',
    courseName: '',
    rollNumber: '',
    semester: '',
  };

  getStudentinfo(blockId: any, seatNo: any) {
    this.student.blockId = blockId;
    this.student.seatNumber = seatNo;
    console.log(this.student);

    this.service.getStudentInfo(this.student).subscribe((res) => {
      if (res !== null) {
        this.studentInfo = res;
      } else {
        console.log(res);
      }
    });
  }

  // Delete Block Method
  deleteBlock(blockId: number) {
    if (!blockId) {
      console.error('Invalid Block ID');
      return;
    }

    // SweetAlert Confirmation before deletion
    Swal.fire({
      title: 'Are you sure?',
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!',
    }).then((result) => {
      if (result.isConfirmed) {
        // Proceed with block deletion
        this.service.deleteBlock(blockId).subscribe(
          (res) => {
            Swal.fire('Deleted!', 'The block has been deleted.', 'success');
            this.fetchBlocks(); // Refresh blocks list after deletion
          },
          (error) => {
            Swal.fire(
              'Failed!',
              'There was an error deleting the block.',
              'error'
            );
          }
        );
      }
    });
  }
}
