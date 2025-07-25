import { CommonModule } from "@angular/common";
import { Component, Inject } from "@angular/core";
import { ReactiveFormsModule, UntypedFormControl, UntypedFormGroup, Validators } from "@angular/forms";
import { MatButtonModule } from "@angular/material/button";
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from "@angular/material/dialog";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { AdminUnitsService } from "src/app/shared/services/admin-units.service";
import swal from "sweetalert2";

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
  templateUrl: "./deleteModal.component.html",
  styleUrls: ["./adminUnitStyles.css", "./deleteModal.component.css"],
})
export class DeleteAdminUnitsyModal {
  adminUnitForm: UntypedFormGroup;

  constructor(
    private dialogRef: MatDialogRef<DeleteAdminUnitsyModal>,
    @Inject(MAT_DIALOG_DATA) public adminUnit: any,
    private adminUnits_: AdminUnitsService
  ) {
    this.initForm();
  }

  deleteAdminUnit(): void {
    if (this.adminUnitForm.invalid && this.adminUnitForm.get("isActive"))
      return;
    this.adminUnitForm.removeControl("confirmData");
    // comentado por fernando
    // this.adminUnits_.deleteAdminUnit(this.adminUnitForm.value).then(() => {
    //   this.dialogRef.close();
    //   swal('Unidad administrativa borrada correctamente', '', 'success')
    // });
  }

  private initForm(): void {
    this.adminUnitForm = new UntypedFormGroup({
      data: new UntypedFormControl(this.adminUnit.data, Validators.required),
      id: new UntypedFormControl(this.adminUnit.id, Validators.required),
      confirmData: new UntypedFormControl("", Validators.required),
      isActive: new UntypedFormControl(false, Validators.requiredTrue),
    });
    this.adminUnitForm
      .get("confirmData")
      .valueChanges.subscribe((response) => {
        const { data } = this.adminUnitForm.value;
        this.adminUnitForm.get("isActive").setValue(data == response);
      });
  }
}
