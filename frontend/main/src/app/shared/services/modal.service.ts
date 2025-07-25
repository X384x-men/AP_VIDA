import { Injectable } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { ModalComponent } from "../components/modal/modal.component";

@Injectable({ providedIn: "root" })
export class ModalService {
  constructor(private dialog: MatDialog) {}

  success(title: string, message: string): void {
    this.show(title, message, "success");
  }

  warning(title: string, message: string): void {
    this.show(title, message, "warning");
  }

  danger(title: string, message: string): void {
    this.show(title, message, "danger");
  }

  info(title: string, message: string): void {
    this.show(title, message, "info");
  }

  confirm(message: string): Promise<any> {
    return this.show('¿Desea continuar?', message, 'info');
  }

  private show(title: string, message: string, type: string): Promise<any> {
    return this.dialog.open(ModalComponent,
      {
        autoFocus: false,
        data: { title, message, type },
        width: 'clamp(300px, 100%, 500px)',
        minHeight: '300px'
      }).afterClosed().toPromise()
  }
}
