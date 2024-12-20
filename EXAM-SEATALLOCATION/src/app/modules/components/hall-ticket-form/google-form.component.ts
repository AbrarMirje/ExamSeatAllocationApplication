import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AdminService } from '../../services/admin.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-google-form',
  templateUrl: './google-form.component.html',
  styleUrls: ['./google-form.component.css'],
})
export class GoogleFormComponent implements OnInit {
  form: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private service: AdminService,
    private toast: ToastrService,
    private router: Router
  ) {
    // Initialize form without validation
    this.form = this.formBuilder.group({
      mobileNumber: [''],
      prnNumber: [''],
    });
  }

  ngOnInit(): void {}

  student: any = {
    mobileNumber: '',
    prnNumber: '',
  };

  formSubmit(): void {
    // Check if form is valid before proceeding (No validation now)
    if (this.form.valid) {
      this.student.mobileNumber = this.form.get('mobileNumber')?.value;
      this.student.prnNumber = this.form.get('prnNumber')?.value;

      this.service.addstudentregistration(this.student).subscribe(
        (res) => {
          console.log(res);

          // Assuming the backend response is the student's details
          if (res) {
            // Store the response (student data) for use in the hall ticket page
            this.service.setStudentData(res);
          }

          this.form.reset();

          this.toast.success(
            'Student Registration successful:',
            'Student Added'
          );
          this.router.navigate(['/hall-ticket']);
        },
        (error) => {
          this.toast.error('Failed to register student', 'Failed!');
        }
      );
    } else {
      // If form is invalid, mark all controls as touched to show validation errors
      this.form.markAllAsTouched();
    }
  }
}
