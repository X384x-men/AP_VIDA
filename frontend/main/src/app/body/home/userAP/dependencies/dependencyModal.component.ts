import { CommonModule } from "@angular/common";
import { Component, Inject } from "@angular/core";
import { UntypedFormGroup, UntypedFormControl, Validators, ReactiveFormsModule } from "@angular/forms";
import { MatButtonModule } from "@angular/material/button";
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from "@angular/material/dialog";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { Dependency } from "src/app/shared/interfaces/dependency.interface";
import { DependenciesService } from "src/app/shared/services/dependencies.service";
import { ModalService } from "src/app/shared/services/modal.service";

@Component({
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule,
    MatDialogModule,
    MatIconModule,
    MatInputModule,
    MatFormFieldModule,
    ReactiveFormsModule
  ],
  templateUrl: "./dependencyModal.component.html",
  styleUrls: ["./dependencyStyles.css"],
})
export class AddDependencieModalComponent {
  dependencyForm: UntypedFormGroup;

  constructor(
    private dialogRef: MatDialogRef<AddDependencieModalComponent>,
    @Inject(MAT_DIALOG_DATA) public dependency: Dependency,
    private dependencies_: DependenciesService,
    private modal_: ModalService
  ) {
    const { data, id } = dependency;
    this.dependencyForm = new UntypedFormGroup({
      data: new UntypedFormControl(data, Validators.required),
      id: new UntypedFormControl(id, Validators.required),
      isActive: new UntypedFormControl(true),
    });
  }

  get modalTitle(): string {
    return (this.isNew ? "Agregar " : "Editar ") + "dependencia";
  }

  public get isNew(): boolean {
    return this.dependency.id <= 0;
  }

  saveDependencie(): void {
    if (this.dependencyForm.invalid) return;
    this.dependencies_.updateDependency(this.dependencyForm.value).then(() => {
      this.dialogRef.close();
      const message = this.isNew ? 'Dependencia Agregada correctamente' : 'Modificación efectuada'
      this.modal_.success('Correcto', message);
    });
  }

  addDependency(): void {
    if (this.isNew) {
      this.dependencies_.dependencies.toPromise().then((result) => {
        const lastDependency = result.reverse()[0];
        this.dependencyForm.get("id").setValue(lastDependency["id"] + 1);
        this.saveDependencie();
      });
    } else {
      this.saveDependencie();
    }
  }
}
