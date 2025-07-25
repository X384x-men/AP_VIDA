import { CommonModule } from "@angular/common";
import { Component, Inject, inject } from "@angular/core";
import { ReactiveFormsModule, UntypedFormControl, UntypedFormGroup, Validators } from "@angular/forms";
import { MatButtonModule } from "@angular/material/button";
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from "@angular/material/dialog";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { DependenciesService } from "src/app/shared/services/dependencies.service";
import swal from "sweetalert2";

@Component({
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule,
    MatDialogModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    ReactiveFormsModule
  ],
  templateUrl: "./deleteModal.component.html",
  styleUrls: ["./dependencyStyles.css", "./deleteModal.component.css"],
})
export class DeleteDependencyModal {
  dependencyForm: UntypedFormGroup;
  private dependencies_ = inject(DependenciesService);

  constructor(
    private dialogRef: MatDialogRef<DeleteDependencyModal>,
    @Inject(MAT_DIALOG_DATA) public dependency: any,
  ) {
    this.initForm();
  }

  deleteDependency(): void {
    if (this.dependencyForm.invalid && this.dependencyForm.get("isActive"))
      return;
    this.dependencyForm.removeControl("confirmData");
    this.dependencies_.deleteDependency(this.dependencyForm.value).then(() => {
      this.dialogRef.close();
      swal('Dependencia borrada correctamente', '', 'success')
    });
  }

  private initForm(): void {
    this.dependencyForm = new UntypedFormGroup({
      data: new UntypedFormControl(this.dependency.data, Validators.required),
      id: new UntypedFormControl(this.dependency.id, Validators.required),
      confirmData: new UntypedFormControl("", Validators.required),
      isActive: new UntypedFormControl(false, Validators.requiredTrue),
    });
    this.dependencyForm
      .get("confirmData")
      .valueChanges.subscribe((response) => {
        const { data } = this.dependencyForm.value;
        this.dependencyForm.get("isActive").setValue(data == response);
      });
  }
}
