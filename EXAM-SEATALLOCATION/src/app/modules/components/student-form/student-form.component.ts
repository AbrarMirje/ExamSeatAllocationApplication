import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AdminService } from '../../services/admin.service';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-student-form',
  templateUrl: './student-form.component.html',
  styleUrls: ['./student-form.component.css'],
})
export class StudentFormComponent implements OnInit {
  form: FormGroup;
  courses: any[] = [];
  students: any[] = []; // Array to store students

  constructor(
    private formBuilder: FormBuilder,
    private service: AdminService,
    private toast: ToastrService
  ) {
    this.form = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      studentClass: ['', Validators.required],
      semester: ['', Validators.required],
      gender: ['', Validators.required],
      mobileNumber: [
        '',
        [Validators.required, Validators.pattern('^[0-9]{10}$')],
      ],
      rollNumber: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],
      year: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.fetchCourses();
  }

  // Fetch courses from the backend service
  fetchCourses(): void {
    this.service.getCourses().subscribe(
      (data: any[]) => {
        this.courses = data;
      },
      (error) => {
        console.error('Error fetching courses', error);
        this.toast.error('Failed to load courses', 'Error');
      }
    );
  }


  // Submit the student form and add the new student
  formSubmit(): void {
    if (this.form.invalid) {
      this.toast.error('Please fill all required fields.', 'Validation Error');
      return;
    }

    const newStudent = {
      firstName: this.form.get('firstName')?.value,
      lastName: this.form.get('lastName')?.value,
      semester: this.form.get('semester')?.value,
      gender: this.form.get('gender')?.value,
      mobileNumber: this.form.get('mobileNumber')?.value,
      rollNumber: this.form.get('rollNumber')?.value,
      year: this.form.get('year')?.value, // Add this to include the student year
      course: {
        courseId: this.form.get('studentClass')?.value,
        courseName: this.courses.find(
          (course) => course.courseId === this.form.get('studentClass')?.value
        )?.courseName,
      },
    };

    // Add new student to the students array
    this.students.push(newStudent);
    // this.toast.success('Student added successfully!', 'Success');

    // Reset the form after adding the student
    this.form.reset();
  }

  // Method to add all students at once
  addAllStudents(): void {
    if (this.students.length === 0) {
      this.toast.error('No students to add.', 'Error');
      return;
    }

    // Send the students array to the service method
    this.service.addStudent(this.students).subscribe(
      (response: any) => {
        // Handle the response here
        this.toast.success('All students added successfully!', 'Success');


        // Process the response structure
        this.handleAddAllResponse(response);
     
        // Optionally reset the students array if needed
        this.students = [];
      },
      (error) => {
        this.toast.error('Error adding students.', 'Error');
        console.error('Error adding students', error);
      }
    );
  }

  // Get the course name based on the courseId
  getCourseName(courseId: number): string {
    const course = this.courses.find((course) => course.courseId === courseId);
    return course ? course.courseName : 'N/A'; // Return 'N/A' if course not found
  }

  // Handle the complex response structure
  handleAddAllResponse(response: any): void {
    // Example: Log the entire response for debugging
    console.log('Response:', response);

    // Extract and process added students from the response
    const addedStudents = response.course.students;

    // Log added students for verification
    addedStudents.forEach((student: any) => {
      console.log('Student Added:', student.firstName, student.lastName);
      // You can process other details as needed
    });

    // Similarly, process seat allocations and other data structures in the response.
    const seatAllocations = addedStudents.flatMap(
      (student: any) => student.seatAllocations
    );

    seatAllocations.forEach((seat: any) => {
      console.log('Seat Allocation:', seat.seatNumber);
      // Process seat allocation details as needed
    });

    // Optional: If needed, store seat allocations or other data for future use
    // this.seatAllocations = seatAllocations;
  }

  // Optional: Add additional methods for seat allocation processing or other response data
} 