import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Component, Inject, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import moment from 'moment';
import { Smartwfm } from 'src/app/core/Util/smartwfm/smartwfm';
import { UserAp } from 'src/app/core/interface/apUser/apUser';
import { SelectMenu } from 'src/app/core/interface/menu/select-menu';
import { AuthenticationService } from 'src/app/core/services/authentication-service/authentication.service';
import { ExcelService } from 'src/app/core/services/excel-service/excel-service.service';
import { SubResourceService } from 'src/app/core/services/service-crud-operations/sub-resource.service';
import { EmailVariable, GlobalVariable, SolicitudVariable } from 'src/app/core/static/variables/url/URLImages';
import { ModalService } from 'src/app/shared/services/modal.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-email-list',
  templateUrl: './email-list.component.html',
  styleUrls: ['./email-list.component.css']
})
export class EmailListComponent {

  apvidaBackground = GlobalVariable.BACKGROUND_IMG_APVIDA;
  solicitudes : any;
  loading = true;
  solicitudesAux = [];
  public dataSource = new MatTableDataSource<UserAp>();
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;


  constructor(
    @Inject("ServiceResource")
    private subResourceService: SubResourceService<any>,
    private authencationService: AuthenticationService
  ) {
    this.getSolicitudes();
  }

  ngOnInit(): void {
    this.authencationService.validacionAdmin();
  }

  ngAfterViewInit():void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  async getSolicitudes(){
    this.subResourceService.list(EmailVariable.LIST_EMAIL,'' ,{ status: 0 })
      .subscribe(data=>{
      this.solicitudes = data;
      this.solicitudesAux = Object.assign([],data);
      this.dataSource.data = this.solicitudes
      }, error=>{
        console.log(error);
      });
    setTimeout(() => {
      this.loading = false;
    }, 500);
  }

  // Filtros nuevos de tabla nueva
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  sendEmail = async (element : any) => {

    const { value: email } = await swal({
      title: "Email a enviar",
      type: "question",
      input: "email",
      inputValue: element.correo,
      showCancelButton: true
    });
    if (email) {
      element.correo = email;
      this.subResourceService.create( element, EmailVariable.REENVIAR_EMAIL ).subscribe(
        data =>
          {
            swal('Éxito', 'El correo a sido enviado', 'success')
          }, error => {
            swal('Alerta', error, 'info');
          }

      )
    }

  }

}
