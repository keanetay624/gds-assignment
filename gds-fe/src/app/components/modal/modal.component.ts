import { Component, Input, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

export interface Data {
  id: string
  name: string
  login: string
  salary: string
  modalTitle: string
  isAdd: boolean
  isDelete: boolean
  isUpdate: boolean
  isView: boolean
}

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css']
})
export class ModalComponent {
  constructor(
    public dialogRef: MatDialogRef<ModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Data,
  ) { }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
