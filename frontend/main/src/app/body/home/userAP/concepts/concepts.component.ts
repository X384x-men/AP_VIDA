import { Component, OnInit} from "@angular/core";
import {  MatDialogModule } from "@angular/material/dialog";
import { MatTableDataSource, MatTableModule } from "@angular/material/table";
import { Concept } from "src/app/shared/interfaces/concept.interface";
import { CommonModule } from "@angular/common";
import { MatIconModule } from "@angular/material/icon";
import { MatDividerModule } from "@angular/material/divider";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatButtonModule } from "@angular/material/button";
import { MatInputModule } from "@angular/material/input";
import { ConceptsService } from "src/app/shared/services/concepts.service";
import swal from "sweetalert2";
import { AuthenticationService } from "src/app/core/services/authentication-service/authentication.service";

@Component({
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatDialogModule,
    MatInputModule,
    MatFormFieldModule,
    MatDividerModule
  ],
  selector: "app-concepts",
  templateUrl: "./concepts.component.html",
  styleUrls: ['./conceptStyles.css']
})
export class ConceptsComponent  implements OnInit {

  userApp : any;
  public dataSource = new MatTableDataSource<Concept>();

  constructor (private conceptsService: ConceptsService,
    private authencationService: AuthenticationService){}

  ngOnInit(){
    this.authencationService.validacionAdmin();
    this.getConceptsInfo();
  }


  openConceptModal() {
    swal({
      title: 'Agrega el nuevo nombre del concepto',
      input: 'text',
      inputAttributes: {
        autocapitalize: 'off'
      },
      showCancelButton: true,
      confirmButtonText: 'Guardar',
      showLoaderOnConfirm: true,
      allowOutsideClick: () => !swal.isLoading()
    }).then((result) => {
      if (result.value) {
        this.conceptsService.newConcept( result.value );
        swal('Concepto actualizado','', 'success' );
        setTimeout(() => {
          this.getConceptsInfo();
        }, 1000);
      }
    })

  }

  updateConcept(concept){

    swal({
      title: 'Editar concepto',
      input: 'text',
      inputAttributes: {
        autocapitalize: 'off'
      },
      inputValue: concept.descripcion,
      showCancelButton: true,
      confirmButtonText: 'Guardar',
      showLoaderOnConfirm: true,
      allowOutsideClick: () => !swal.isLoading()
    }).then((result) => {
      if (result.value) {
        this.conceptsService.updateConcept( concept.idCatalogoConceptos, result.value );
        swal('Concepto actualizado','', 'success' );
        setTimeout(() => {
          this.getConceptsInfo();
        }, 1000);
      }
    })

  }

  deleteConcept(concept){

    return new Promise((resolve, reject)=>{
      swal('Concepto eliminado','', 'success' ).then((result) => {
        if(result.value){
          resolve(true)
          let prueba = this.conceptsService.deleteConcept( concept.idCatalogoConceptos, concept.status = 0 );
          if (prueba) {
            this.initConcepts();

          }
        }else{
          reject(false);
          console.log('chao');
        }
      })});

  }

  filterConcepts (filterValue: string){
    this.dataSource.filter = filterValue.trim().toUpperCase();
  }

  private initConcepts(){
    this.dataSource = new MatTableDataSource();
    this.getConceptsInfo()
  }


  getConceptsInfo(){
    this.conceptsService.getConcepts()
      .subscribe((res)=>{
        this.dataSource.data = res.filter( data => data.status === 1 );
      })
  }
}
