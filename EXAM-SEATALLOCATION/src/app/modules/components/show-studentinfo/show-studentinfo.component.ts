import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import jsPDF from 'jspdf';

@Component({
  selector: 'app-show-studentinfo',
  templateUrl: './show-studentinfo.component.html',
  styleUrls: ['./show-studentinfo.component.css'],
})
export class ShowStudentinfoComponent implements OnInit {
  students: any[] = [];
  filteredStudents: any[] = [];
  courses: any[] = [];
  years: string[] = ['I', 'II', 'III', 'IV'];
  selectedCourse: string = '';
  selectedYear: string = '';
  searchQuery: string = ''; // Property to store search query

  constructor(private service: AdminService) {}

  ngOnInit(): void {
    this.getStudents(); // Fetch all students initially
    this.service.getCourses().subscribe(
      (data) => {
        this.courses = data;
      },
      (error) => console.error('Error fetching courses:', error)
    );
  }
  downloadPdf(): void {
    const doc = new jsPDF();
  
    // Add a title to the PDF
    doc.text('Filtered Student Records', 14, 10);
  
    // Prepare table headers and rows
    const headers = [['Sr. No', 'PRN Number', 'Name', 'Branch', 'Semester', 'Mobile No', 'Seat Number', 'Block No']];
    const rows = this.filteredStudents.map((student, index) => [
      index + 1,
      student.prnNumber,
      `${student.firstName} ${student.lastName}`,
      student.courseName,
      student.semester,
      student.mobileNumber,
      student.seatNumber,
      student.blockNumber,
    ]);
  

  
    // Save the PDF
    doc.save('Filtered_Student_Records.pdf');
  }

  // Fetch all students
  getStudents(): void {
    this.service.getAllStudents().subscribe(
      (data: any[]) => {
        this.students = data;
        this.filteredStudents = data; // Initially display all students
      },
      (error) => console.error('Error fetching students:', error)
    );
  }

  // Handle course selection
  onCourseChange(): void {
    this.selectedYear = ''; // Reset year selection when course changes
    this.filteredStudents = []; // Clear students list until year is selected
  }

  // Fetch students based on selected course and year
  filterStudents(): void {
    if (this.selectedCourse && this.selectedYear) {
      // Find courseId for the selected courseName
      const selectedCourseObj = this.courses.find(
        (course) => course.courseName === this.selectedCourse
      );

      if (selectedCourseObj) {
        const courseId = selectedCourseObj.courseId; // Assuming backend returns courseId
        const year = this.selectedYear; // Selected year in Roman

        this.service.getStudentsByCourse(courseId, year).subscribe(
          (data) => {
            this.filteredStudents = data; // Replace with filtered data from API
          },
          (error) =>
            console.error('Error fetching students by course and year:', error)
        );
      }
    } else {
      this.filteredStudents = []; // Clear students if criteria is not met
    }
  }

  // Filter students based on search query
  searchStudents(): void {
    if (this.searchQuery.trim()) {
      const query = this.searchQuery.toLowerCase();
  
      // Filter students by matching any relevant field
      this.filteredStudents = this.students.filter((student) =>
        Object.values(student).some((value) =>
          value?.toString().toLowerCase().includes(query)
        )
      );
    } else {
      this.filteredStudents = this.students; // Reset to all students if search query is empty
    }
  }
}
