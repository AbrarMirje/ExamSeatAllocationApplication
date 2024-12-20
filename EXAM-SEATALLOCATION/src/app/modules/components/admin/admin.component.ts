import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-admin-login',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css'],
})
export class AdminComponent {
  admin = {
    email: '',
    password: '',
  };

  constructor(
    private http: HttpClient,
    private router: Router,
    private toastr: ToastrService
  ) {}

  // Handle form submission
  onSubmit(): void {
    const url = 'http://localhost:9090/user/login-user'; // Replace with your actual backend URL
    const params = { email: this.admin.email, password: this.admin.password };

    this.http.get<boolean>(url, { params }).subscribe({
      next: (isValid: boolean) => {
        if (isValid) {
          this.toastr.success('Login Successful', 'Success');
          this.router.navigate(['/dashboard']);
        } else {
          this.toastr.error('Invalid credentials', 'Error');
        }
      },
      error: (error) => {
        console.error('Login failed:', error);
        this.toastr.error('Something went wrong', 'Error');
      },
    });
  }
}
