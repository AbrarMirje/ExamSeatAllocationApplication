import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AdminService } from '../../services/admin.service';
import { ToastrService } from 'ngx-toastr';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-block',
  templateUrl: './add-block.component.html',
  styleUrls: ['./add-block.component.css'],
})
export class AddBlockComponent implements OnInit {
  form: FormGroup;
  blocks: any[] = [];
  filteredBlocks: any[] = [];
  editMode: boolean = false;
  selectedBlockId: number | null = null;
  searchTerm: string = ''; // For dynamic search

  constructor(
    private service: AdminService,
    private formBuilder: FormBuilder,
    private toast: ToastrService
  ) {
    this.form = this.formBuilder.group({
      blockId: [null], // Add blockId to the form
      blockNumber: ['', [Validators.required]],
      capacity: ['', [Validators.required]],
    });
  }

  ngOnInit(): void {
    this.fetchBlocks();
  }

  // Fetch all blocks
  fetchBlocks() {
    this.service.getBlocks().subscribe(
      (res: any) => {
        this.blocks = res;
        this.filteredBlocks = res; // Initially display all blocks
      },
      (error) => {
        this.toast.error('Error fetching blocks', 'Failed!');
      }
    );
  }

  // Filter blocks based on search term
  filterBlocks() {
    const search = this.searchTerm.toLowerCase();
    this.filteredBlocks = this.blocks.filter(
      (block) =>
        block.blockNumber.toLowerCase().includes(search) ||
        block.capacity.toString().includes(search)
    );
  }

  // Add or update block
  formSubmit() {
    if (this.form.valid) {
      if (this.editMode && this.selectedBlockId) {
        // Update Block (send blockId with the block object)
        const updatedBlock = {
          ...this.form.value,
          blockId: this.selectedBlockId,
        };
        this.service.updateBlock(updatedBlock).subscribe(
          (res) => {
            this.toast.success('Block updated successfully', 'Updated!');
            this.fetchBlocks(); // Refresh the blocks list
            this.cancelEdit(); // Reset form and exit edit mode
          },
          (error) => {
            this.toast.error('Error updating block', 'Failed!');
          }
        );
      } else {
        // Add Block
        this.service.addBlock(this.form.value).subscribe(
          (res) => {
            this.toast.success('Block added successfully', 'Block Added');
            this.form.reset();
            this.fetchBlocks(); // Refresh the blocks list
          },
          (error) => {
            this.toast.error('Error adding block', 'Failed!');
          }
        );
      }
    } else {
      this.toast.error(
        'Please fill in all required fields',
        'Validation Error'
      );
    }
  }

  // Delete block
  deleteBlock(blockId: number) {
    if (!blockId) {
      this.toast.error('Invalid Block ID', 'Failed!');
      return;
    }

    // Show SweetAlert confirmation before deleting
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
        // Proceed with deletion if confirmed
        this.service.deleteBlock(blockId).subscribe(
          (res) => {
            this.toast.success('Block deleted successfully', 'Deleted!');
            this.fetchBlocks();
          },
          (error) => {
            this.toast.error('Error deleting block', 'Failed!');
          }
        );
      }
    });
  }

  // Edit block
  editBlock(block: any) {
    this.editMode = true;
    this.selectedBlockId = block.blockId;
    this.form.patchValue({
      blockId: block.blockId, // Add blockId to the form
      blockNumber: block.blockNumber,
      capacity: block.capacity,
    });
  }

  // Cancel edit mode
  cancelEdit() {
    this.editMode = false;
    this.selectedBlockId = null;
    this.form.reset();
  }
}
