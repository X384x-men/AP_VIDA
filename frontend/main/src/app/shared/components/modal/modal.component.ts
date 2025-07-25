import { Component, Inject } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";

interface ModalData {
  title: string;
  subtitle: string;
  message: string;
  type: 'success' | 'warning' | 'danger';
  route: string;
}

@Component({
  template: `
    <div [class]="data.type + ' header'">
      <mat-icon>{{iconType}}</mat-icon>
    </div>
    <h1 class="headline-2">{{data.title}}</h1>
    <div mat-dialog-content>
      {{data.message}}
    </div>
    <div mat-dialog-actions>
      <button mat-button [mat-dialog-close]="false">CERRAR</button>
      <button mat-button [mat-dialog-close]="true" (click)="closeModal()" [class]="data.type" *ngIf="iconType == 'info_circle'">
        ENTENDIDO</button>
    </div>
  `,
  styles:[`
    .header {
      display: flex;
      padding: 4px;
      justify-content: center;
    }
    .header.success {
      background: #28a745;
      color: #eee;
    }
    .header.warning {
      background: #ffeb3b
    }
    .header.danger {
      background: #f44336;
      color: #eee
    }
    .header.info {
      background: #2196f3
    }
    .header > mat-icon {
      font-size: 48px;
      width: 48px;
      height: 48px;
    }
    h1 {
      text-align: center;
    }
    .mat-dialog-actions {
      justify-content: center
    }
    button.success {
      color: #28a745;
    }
  `]
})
export class ModalComponent {
  private types: object;

  constructor(private dialogRef: MatDialogRef<ModalComponent>, @Inject(MAT_DIALOG_DATA) public data: ModalData) {
    this.types = {
      success: 'check_circle',
      warning: 'warning',
      danger: 'dangerous',
      info: 'info_circle',
    }
  }

  get iconType(): string { return this.types[this.data.type]; }

  closeModal(): void {
    this.dialogRef.close();
  }
}
