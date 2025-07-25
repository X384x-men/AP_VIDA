import { Component, OnInit } from "@angular/core";
import { MatDialog, MatDialogModule,  MatDialogRef } from "@angular/material/dialog";
import { MatTableDataSource, MatTableModule } from "@angular/material/table";
import { addAdminUnitModalComponent } from "./adminUnitModal.component";
import { AdminUnitsService } from "src/app/shared/services/admin-units.service";
import { DeleteAdminUnitsyModal } from "./deleteAdminUnit.component";
import { CommonModule } from "@angular/common";
import { MatButtonModule } from "@angular/material/button";
import { MatDividerModule } from "@angular/material/divider";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { AdminUnit } from "src/app/shared/interfaces/admin-unit.interface";
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
    MatDividerModule,
  ],
  selector: "app-admin-units",
  templateUrl: "./admin-units.component.html",
  styleUrls: ["./adminUnitStyles.css"],
})
export class AdminUnitComponent implements OnInit {
  userApp : any;
  public dataSource = new MatTableDataSource<AdminUnit>();
  constructor (private adminUnitsService: AdminUnitsService,
    private authencationService: AuthenticationService){}

  ngOnInit(){
    this.authencationService.validacionAdmin();
    this.getAdminUnitsInfo();
  }


  openAdminUnitModal() {
    swal({
      title: 'Agrega el nombre de la unidad',
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
        this.adminUnitsService.newAdminUnit( result.value );
        swal('Unidad actualizada','', 'success' );
        setTimeout(() => {
          this.getAdminUnitsInfo();
        }, 1000);
      }
    })
  }

  updateAdminUnit(adminUnit) {
    swal({
      title: 'Editar Unidad',
      input: 'text',
      inputAttributes: {
        autocapitalize: 'off'
      },
      inputValue: adminUnit.descripcion,
      showCancelButton: true,
      confirmButtonText: 'Guardar',
      showLoaderOnConfirm: true,
      allowOutsideClick: () => !swal.isLoading()
    }).then((result) => {
      if (result.value) {
        this.adminUnitsService.updateAdminUnit(  adminUnit.idUnidadAdministrativa, result.value );
        swal('Unidad actualizada','', 'success' );
        setTimeout(() => {
          this.getAdminUnitsInfo();
        }, 1000);
      }
    })
  }

  deleteAdminUnit(adminUnit) {

    return new Promise((resolve, reject)=>{
      swal('Unidad eliminada','', 'success' ).then((result) => {
        if(result.value){
          resolve(true)
          let prueba = this.adminUnitsService.deleteAdminUnit( adminUnit.idUnidadAdministrativa, adminUnit.status = 0 );
          if (prueba) {
            this.initAdminUnits();
          }
        }else{
          reject(false);
        }
      })});
  }

  filterAdminUnit(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toUpperCase();
  }

  private initAdminUnits() {
    this.dataSource = new MatTableDataSource();
    this.getAdminUnitsInfo()
  }

  getAdminUnitsInfo(){
    this.adminUnitsService.getAdminUnits()
      .subscribe((res)=>{
        this.dataSource.data = res.filter( data => data.status === 1 );
    })
  }
}
