import { CommonModule } from "@angular/common";
import { Component, Inject } from "@angular/core";
import { UntypedFormGroup, UntypedFormControl, Validators, ReactiveFormsModule } from "@angular/forms";
import { MatButtonModule } from "@angular/material/button";
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from "@angular/material/dialog";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { AdminUnit } from "src/app/shared/interfaces/admin-unit.interface";
import { AdminUnitsService } from "src/app/shared/services/admin-units.service";
import { ModalService } from "src/app/shared/services/modal.service";

@Component({
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule,
    MatIconModule,
    MatDialogModule,
    MatInputModule,
    MatFormFieldModule,
    ReactiveFormsModule
  ],
  templateUrl: "./adminUnitModal.component.html",
  styleUrls: ["./adminUnitStyles.css"],
})
export class addAdminUnitModalComponent {
  adminUnitForm: UntypedFormGroup;

  constructor(
    private dialogRef: MatDialogRef<addAdminUnitModalComponent>,
    @Inject(MAT_DIALOG_DATA) public adminUnit: AdminUnit,
    private adminUnit_: AdminUnitsService,
    private modal_: ModalService
  ) {
    const { data, id } = adminUnit;
    this.adminUnitForm = new UntypedFormGroup({
      data: new UntypedFormControl(data, Validators.required),
      id: new UntypedFormControl(id, Validators.required),
      isActive: new UntypedFormControl(true),
    });
  }

  get modalTitle(): string {
    return (this.isNew ? "Agregar " : "Editar ") + "unidad administrativa";
  }

  public get isNew(): boolean {
    return this.adminUnit.id <= 0;
  }

  saveAdminUnit(): void {
    if (this.adminUnitForm.invalid) return;
    // comentado por fernando
    // this.adminUnit_.updateAdminUnit(this.adminUnitForm.value).then(() => {
    //   this.dialogRef.close();
    //   const message = this.isNew ? 'Unidad adminstrativa agregada correctamente' : 'Modificación efectuada'
    //   this.modal_.success('Correcto', message);
    // });
  }

  addAdminUnit(): void {
    if (this.isNew) {
      this.adminUnit_.adminUnits.toPromise().then((result) => {
        const lastAdminUnit = result.reverse()[0];
        this.adminUnitForm.get("id").setValue(lastAdminUnit["id"] + 1);
        this.saveAdminUnit();
      });
    } else {
      this.saveAdminUnit();
    }
  }
}
