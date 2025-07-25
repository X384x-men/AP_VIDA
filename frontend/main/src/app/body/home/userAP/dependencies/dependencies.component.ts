import { Component, OnInit } from "@angular/core";
import { MatDialogModule } from "@angular/material/dialog";
import { MatTableDataSource, MatTableModule } from "@angular/material/table";
import { DependenciesService } from "src/app/shared/services/dependencies.service";
import { CommonModule } from "@angular/common";
import { MatButtonModule } from "@angular/material/button";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { MatDividerModule } from "@angular/material/divider";
import { Dependency } from "src/app/shared/interfaces/dependency.interface";
import swal from 'sweetalert2'

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
  templateUrl: "./dependencies.component.html",
  styleUrls: ['./dependencyStyles.css']
})
export class DependenciesComponent  implements OnInit{

  public dataSource = new MatTableDataSource<Dependency>();

  constructor (
    private dependenciesService: DependenciesService){}

  ngOnInit(){
    this.getDependenciesInformation();
  }

  openDependencieModal() {
    swal({
      title: 'Agrega el nuevo nombre de la dependencia',
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
        this.dependenciesService.newDependency( result.value );
        swal('Dependencia actualizada','', 'success' );
        setTimeout(() => {
          this.getDependenciesInformation()
        }, 1000);
      }
    })
  }

  updateDependency(dependency){
    swal({
      title: 'Editar dependencia',
      input: 'text',
      inputAttributes: {
        autocapitalize: 'off'
      },
      inputValue: dependency.descripcionCatalogo,
      showCancelButton: true,
      confirmButtonText: 'Guardar',
      showLoaderOnConfirm: true,
      allowOutsideClick: () => !swal.isLoading()
    }).then((result) => {
      if (result.value) {
        this.dependenciesService.updateDependency( result.value, dependency.idCatalogo );
        swal('Dependencia actualizada','', 'success' );
        setTimeout(() => {
           this.getDependenciesInformation()
        }, 1000);
      }
    })
  }

  deleteDependency(dependency: Dependency) {
    return new Promise((resolve, reject)=>{
    swal('Dependencia eliminada','', 'success' ).then((result) => {
      if(result.value){
        resolve(true)
        this.dependenciesService.deleteDependency( dependency.status = 0, dependency.idCatalogo );
        setTimeout(() => {
          this.getDependenciesInformation()
        }, 1000);
      }else{
        reject(false);
      }
    })});
  }

  filterDependencies (filterValue: string) {
    this.dataSource.filter = filterValue.trim().toUpperCase();
  }

  private initDependencies(): void {
    this.dataSource = new MatTableDataSource();
    this.getDependenciesInformation();

  }

  getDependenciesInformation(){
    this.dependenciesService.getDependencies()
      .subscribe((res)=>{
        this.dataSource.data = res.filter( data => data.status === 1 );
      })
  }
}
