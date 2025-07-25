import { Component, Input } from "@angular/core";
import { SubResourceService } from "src/app/core/services/service-crud-operations/sub-resource.service";
import { EmailVariable } from "src/app/core/static/variables/url/URLImages";
import { ModalService } from "../../services/modal.service";

@Component({
  selector: 'app-reset-password-button',
  template: `
    <button mat-menu-item (click)="resetPassword()">
      <mat-icon>key</mat-icon>
      Restablecer contraseña
    </button>
  `
})
export class ResetPasswordButton {
  @Input() data: { rfc: string; email: string } | undefined;

  constructor(
    private subResourceService: SubResourceService<any>,
    private modalService_: ModalService
  ) {}

  resetPassword() {
    if (this.data == undefined) return;
    this.subResourceService
      .create(this.data, EmailVariable.SEND_EMAIL)
      .subscribe(
        (data) => {
          this.modalService_.success(
            "Listo",
            "Se enviaron instrucciones al correo del cliente para restablecer su contraseña"
          );
        },
        (error) => {
          this.modalService_.danger("Ocurrió un error", error);
        }
      );
  }
}
