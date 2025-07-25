import { CommonModule } from "@angular/common";
import { Component, Inject } from "@angular/core";
import { Validators, FormControl, FormGroup, ReactiveFormsModule } from "@angular/forms";
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
    MatButtonModule,
    MatIconModule,
    MatDialogModule,
    MatInputModule,
    MatFormFieldModule,
    ReactiveFormsModule
  ],
  templateUrl: "./conceptModal.component.html",
  styleUrls: ["./conceptStyles.css"],
})
export class addConceptModalComponent {
  ConceptForm: FormGroup;

  constructor(
    private dialogRef: MatDialogRef<addConceptModalComponent>,
    @Inject(MAT_DIALOG_DATA) public concept: Concept,
    private concepts_: ConceptsService,
    private modal_: ModalService
  ) {
    const { description, id } = concept;
    this.ConceptForm = new FormGroup({
      description: new FormControl<string>(description, Validators.required),
      id: new FormControl<number>(id, Validators.required),
      isActive: new FormControl<boolean>(true),
    });
  }

  get modalTitle(): string {
    return (this.isNew ? "Agregar " : "Editar ") + "concepto";
  }

  public get isNew(): boolean {
    return this.concept.id <= 0;
  }

  saveConcept(): void {
    if (this.ConceptForm.invalid) return;
    // comentado por fernando
    // this.concepts_.updateConcept(this.ConceptForm.value).then(() => {
    //   this.dialogRef.close();
    //   const message = this.isNew ? 'Concepto agregado correctamente' : 'Modificación efectuada'
    //   this.modal_.success('Correcto', message);
    // });
  }

  addConcept(): void {
    if (this.isNew) {
      this.concepts_.concepts.toPromise().then((result) => {
        const lastConcept = result.reverse()[0];
        this.ConceptForm.get("id").setValue(lastConcept["id"] + 1);
        this.saveConcept();
      });
    } else {
      this.saveConcept();
    }
  }
}
