import { CommonModule } from "@angular/common";
import { Component, Inject, inject } from "@angular/core";
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { MatButtonModule } from "@angular/material/button";
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from "@angular/material/dialog";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { Concept } from "src/app/shared/interfaces/concept.interface";
import { ConceptsService } from "src/app/shared/services/concepts.service";
import { ModalService } from "src/app/shared/services/modal.service";

@Component({
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule,
    MatDialogModule,
    MatFormFieldModule,
    MatIconModule,
  ],
  templateUrl: "./deleteModal.component.html",
  styleUrls: ["./conceptStyles.css", "./deleteModal.component.css"],
})
export class DeleteConceptModal {
  conceptForm: FormGroup;
  private modal_: ModalService = inject(ModalService)

  constructor(
    private dialogRef: MatDialogRef<DeleteConceptModal>,
    @Inject(MAT_DIALOG_DATA) public concept: Concept,
    private concepts_: ConceptsService,
  ) {
    this.initForm();
  }

  deleteConcept(): void {
    if (this.conceptForm.invalid && this.conceptForm.get("isActive"))
      return;
    this.conceptForm.removeControl("confirmData");
    // comentado por fernando
    // this.concepts_.deleteConcept(this.conceptForm.value).then(() => {
    //   this.dialogRef.close();
    //   this.modal_.success('Concepto borrado correctamente', '')
    // });
  }

  private initForm(): void {
    this.conceptForm = new FormGroup({
      description: new FormControl(this.concept.description, Validators.required),
      id: new FormControl(this.concept.id, Validators.required),
      confirmData: new FormControl("", Validators.required),
      isActive: new FormControl(false, Validators.requiredTrue),
    });
    this.conceptForm
      .get("confirmData")
      .valueChanges.subscribe((response) => {
        const { description } = this.conceptForm.value;
        this.conceptForm.get("isActive").setValue(description == response);
      });
  }
}
