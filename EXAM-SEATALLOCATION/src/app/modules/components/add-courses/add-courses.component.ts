import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-courses',
  templateUrl: './add-courses.component.html',
  styleUrls: ['./add-courses.component.css'],
})
export class AddCoursesComponent implements OnInit {
  courses: any[] = []; // Original list of courses fetched from the API
  filteredCourses: any[] = []; // To store filtered courses based on the search
  searchTerm: string = ''; // Two-way bound to the search input box
  isEditing: boolean = false; // Flag to check if we are in edit mode
  editingCourseId: number | null = null; // Stores the courseId of the course being edited

  form: FormGroup = this.formBuilder.group({
    courseName: ['', [Validators.required]],
  });

  constructor(
    private service: AdminService,
    private formBuilder: FormBuilder,
    private toast: ToastrService
  ) {}

  ngOnInit(): void {
    this.fetchCourses();
  }

  fetchCourses(): void {
    this.service.getCourses().subscribe(
      (response) => {
        this.courses = response;
        this.filteredCourses = [...response];
      },
      (error) => {
        console.error('Error fetching courses:', error);
        this.toast.error('Failed to load courses.', 'Error');
      }
    );
  }

  formSubmit(): void {
    if (this.form.valid) {
      const courseData = this.form.value;

      if (this.isEditing && this.editingCourseId !== null) {
        // Update the course
        const updatedCourse = { courseId: this.editingCourseId, ...courseData };

        this.service.updateCourse(updatedCourse).subscribe(
          (response) => {
            if (response === true) {
             
              this.toast.success('Course updated successfully!', 'Success');
              this.updateCourseInList(updatedCourse);
              this.resetForm();
            } else {
            
              this.toast.error(
                'Cannot update course as it is associated with students.',
                'Update Failed'
              );
            }
          },
          (error) => {
            console.error('Error updating course:', error);
            this.toast.error('Error occurred while updating the course.', 'Error');
          }
        );
      } else {
        // Add a new course
        this.service.addCourses(courseData).subscribe(
          (newCourse) => {
            this.courses.push(newCourse);
            this.filteredCourses.push(newCourse);
            this.toast.success('Course added successfully!', 'Success');
            this.form.reset();
          },
          (error) => {
            console.error('Error adding course:', error);
            this.toast.error('Error adding course', 'Failed');
          }
        );
      }
    }
  }

  updateCourseInList(updatedCourse: any): void {
    const index = this.courses.findIndex(
      (course) => course.courseId === updatedCourse.courseId
    );
    if (index > -1) {
      this.courses[index] = updatedCourse;
      this.filterCourse(); // Ensure filtered list is also updated
    }
  }

  startEditing(course: any): void {
    this.isEditing = true;
    this.editingCourseId = course.courseId;
    this.form.patchValue({
      courseName: course.courseName,
    });
  }

  resetForm(): void {
    this.isEditing = false;
    this.editingCourseId = null;
    this.form.reset();
  }

  deleteCourse(courseId: number): void {
    Swal.fire({
      title: 'Are you sure?',
      text: 'Do you really want to delete this course?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Yes, delete it!',
    }).then((result) => {
      if (result.isConfirmed) {
        this.service.deleteCourses(courseId).subscribe(
          (response) => {
          
              // Display success message
              this.toast.success('Course deleted successfully!', 'Success');
              this.fetchCourses()
              
              // Refresh the page
              setTimeout(() => {
                location.reload();
              }, 2000); // Adds a small delay before refresh
           
            
          },
          (error) => {
            // Handle server errors
            console.error('Error deleting course:', error);
            this.toast.error(
              'Cannot delete course as it is associated with students.',
              'Deletion Failed'
            );
          }
        );
      }
    });
  }
  
  filterCourse(): void {
    if (this.searchTerm.trim() === '') {
      this.filteredCourses = [...this.courses]; // Reset to the full list
    } else {
      this.filteredCourses = this.courses.filter((course) =>
        course.courseName
          .toLowerCase()
          .includes(this.searchTerm.toLowerCase().trim())
      );
    }
  }
}
