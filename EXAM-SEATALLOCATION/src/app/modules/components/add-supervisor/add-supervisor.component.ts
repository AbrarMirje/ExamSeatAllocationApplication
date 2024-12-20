// TypeScript Code
import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { ChangeDetectorRef } from '@angular/core';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-supervisor',
  templateUrl: './add-supervisor.component.html',
  styleUrls: ['./add-supervisor.component.css'],
})
export class AddSupervisorComponent implements OnInit {
  form!: FormGroup;
  blocks: any[] = [];
  supervisors: any[] = [];
  filteredSupervisors: any[] = [];
  searchTerm: string = '';
  selectedSupervisorId: number | null = null;
  isUpdating: boolean = false;

  constructor(
    private service: AdminService,
    private formBuilder: FormBuilder,
    private toast: ToastrService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      supervisorName: ['', [Validators.required, Validators.pattern(/^[a-zA-Z\s]*$/)]],
      contact: ['', [Validators.required, Validators.pattern(/^\d{10}$/)]],
      email: ['', [Validators.required, Validators.email]],
      departmentName: ['', [Validators.required]],
      blockNumber: [''],
    });

    this.fetchBlocks();
    this.fetchSupervisors();
  }

  fetchBlocks(): void {
    this.service.getAvailableBlocks().subscribe(
      (response: any) => {
        this.blocks = [...response];
        this.cdr.detectChanges();
      },
      (error) => {
        this.toast.error('Error fetching blocks', 'Failed!');
      }
    );
  }

  fetchSupervisors(): void {
    this.service.getSupervisors().subscribe(
      (response: any) => {
        this.supervisors = [...response];
        this.filteredSupervisors = [...response];
        this.updateBlockAvailability();
        this.cdr.detectChanges();
      },
      (error) => {
        this.toast.error('Error fetching supervisors', 'Failed!');
      }
    );
  }

  updateBlockAvailability(): void {
    const allocatedBlockIds = this.supervisors
      .filter((supervisor) => supervisor.isBlockAllocated)
      .map((supervisor) => supervisor.blockId);

    this.blocks = this.blocks.filter(
      (block) => !allocatedBlockIds.includes(block.blockId)
    );
    this.cdr.detectChanges();
  }

  formSubmit(): void {
    if (this.form.valid) {
      const supervisorData = {
        supervisorId: this.selectedSupervisorId || 0,
        superVisorName: this.form.get('supervisorName')?.value,
        contactNumber: this.form.get('contact')?.value,
        email: this.form.get('email')?.value,
        departName: this.form.get('departmentName')?.value,
        block: { blockId: this.form.get('blockNumber')?.value || null },
        blockAllocated: true,
      };

      if (this.isUpdating && this.selectedSupervisorId) {
        this.service.updateSupervisor(supervisorData).subscribe(
          (response) => {
            this.toast.success('Supervisor updated successfully', 'Success');
            this.resetForm();
            this.fetchSupervisors();
          },
          (error) => {
            this.toast.success('Supervisor updated successfully', 'Success');
            setTimeout(() => location.reload(), 2000); 
          }
        );
      } else {
        this.service.addSupervisor([supervisorData]).subscribe(
          (response) => {
            this.toast.success('Supervisor added successfully', 'Success');
            this.resetForm();
            this.fetchSupervisors();
            setTimeout(() => location.reload(), 1000);
          },
          (error) => {
            this.toast.error('Error adding supervisor', 'Failed!');
          }
        );
      }
    } else {
      this.toast.error('Please fill all required fields correctly', 'Validation Error!');
    }
  }

  deleteSupervisor(supervisorId: number): void {
    Swal.fire({
      title: 'Are you sure?',
      text: 'Do you want to delete this supervisor?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Yes, delete it!',
    }).then((result) => {
      if (result.isConfirmed) {
        this.service.deleteSupervisor(supervisorId).subscribe(
          (res) => {
            this.toast.success('Supervisor deleted successfully', 'Success');
            this.fetchSupervisors();
          },
          () => {
            this.toast.success('Supervisor deleted successfully', 'Success');
            setTimeout(() => location.reload(), 200); 
          }
        );
      }
    });
  }

  updateSupervisor(supervisor: any): void {
    this.selectedSupervisorId = supervisor.supervisorId;
    this.isUpdating = true;
    this.form.patchValue({
      supervisorName: supervisor.superVisorName,
      contact: supervisor.contactNumber,
      email: supervisor.email,
      departmentName: supervisor.departName,
      blockNumber: supervisor.blockId,
    });
  }

  filterSupervisors(): void {
    const term = this.searchTerm.trim().toLowerCase();

    this.filteredSupervisors = this.supervisors.filter((supervisor) => {
      const supervisorName = supervisor.superVisorName?.toLowerCase() || '';
      const contactNumber = supervisor.contactNumber?.toString() || '';
      const email = supervisor.email?.toLowerCase() || '';
      const departName = supervisor.departName?.toLowerCase() || '';
      const blockNumber = supervisor.block?.blockNumber?.toString() || '';

      return (
        supervisorName.includes(term) ||
        contactNumber.includes(term) ||
        email.includes(term) ||
        departName.includes(term) ||
        blockNumber.includes(term)
      );
    });
  }

  resetForm(): void {
    this.form.reset();
    this.selectedSupervisorId = null;
    this.isUpdating = false;
  }
}
