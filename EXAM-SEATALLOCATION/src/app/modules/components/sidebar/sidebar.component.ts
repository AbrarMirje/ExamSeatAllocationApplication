import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css',
})
export class SidebarComponent {
  private apiUrl =
    'http://localhost:9090/seat-allocation/clear-seat-allocation';

  private apiUrl2 = 'http://localhost:9090/seat-allocation/allocate-seats'; // Replace with your actual API URL

  constructor(private http: HttpClient, private toastr: ToastrService) {}

  clearAllSeatsAllocation(): void {
    Swal.fire({
      title: 'Are you sure?',
      text: 'This will clear all seat allocations. This action cannot be undone.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, clear all!',
      cancelButtonText: 'Cancel',
    }).then((result) => {
      if (result.isConfirmed) {
        this.http.delete(this.apiUrl, { responseType: 'text' }).subscribe({
          next: (response: string) => {
            const message =
              response && response.trim()
                ? response
                : 'All seat allocations cleared successfully!';
            Swal.fire('Success', message, 'success');
            window.location.reload();
            // Update the UI dynamically here if needed
            // Remove the reload and ensure UI updates reflect the change
          },
          error: (error) => {
            Swal.fire(
              'Error',
              'Failed to clear seat allocations. Please try again.',
              'error'
            );
            console.error('Error clearing seat allocations:', error);
          },
        });
      }
    });
  }

  reallocateSeats(): void {
    Swal.fire({
      title: 'Are you sure?',
      text: 'This will reallocate all seat allocations. This action cannot be undone.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, reallocate!',
      cancelButtonText: 'Cancel',
    }).then((result) => {
      if (result.isConfirmed) {
        this.http
          .post<string>(this.apiUrl2, {}, { responseType: 'text' as 'json' })
          .subscribe({
            next: (response: string) => {
              const message =
                response && response.trim()
                  ? response
                  : 'All seat allocations reallocated successfully!';
              Swal.fire('Success', message, 'success');
              window.location.reload();
              // Update the UI dynamically here if applicable
            },
            error: (error) => {
              Swal.fire(
                'Error',
                'Failed to reallocate seat allocations. Please try again.',
                'error'
              );
              console.error('Error reallocating seat allocations:', error);
            },
          });
      }
    });
  }
}
