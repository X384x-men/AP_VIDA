import { Resource } from 'src/app/core/class/http-resource/resource';
import { ViewChild, Input, Output, EventEmitter, AfterContentInit } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { HttpResource } from 'src/app/core/class/http-resource/http-resource';
import { SubResourceService } from 'src/app/core/services/service-crud-operations/sub-resource.service';
import { RoutingUtilities } from 'src/app/core/Util/routing/routing-utilities';
import { ActivatedRoute, Router } from '@angular/router';
import { Smartwfm } from 'src/app/core/Util/smartwfm/smartwfm';
import { TablePropertiesComponent, DefaultColumns } from './table-button-component';

interface DataTable {
  jsonPath?: string;
  obj: any;
  isHaveRootArray: boolean;
}
export abstract class TableComponent<T extends Resource> extends TablePropertiesComponent implements AfterContentInit {
  /**
   * DESCRIPBION QUE APARECERA EN EL ENCABEZADO DE UNA TABLA
   */
  @Input() caption = 'Listado de informacion';
  @Input() columnsToBlock: string[];
  @Input() dataTable: DataTable;
  @Input() useDefaulMock = false;
  /**
   * Paginador de la tabla
   */
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  /**
   * Emite true si se ha pulsado el boton de regresar
   */
  @Output() back = new EventEmitter<boolean>();
  /**
   * Ordenamiento de la tabla
   */
  @ViewChild(MatSort, {static: false}) sort: MatSort;
  /**
   * Necesaria para realizar acciones en la tabla
   * url:servicio que se consultara para realizar la peticion http y obtener los datos de la tabla
   * params: parametros que necesita el servicio (opcional)
   * navigate: Indica si al pulsar una columna en la tabla, se navegara a otra vista
   * method: metodo de acceso del servicio java
   * table: indica que columna y servicio deberan ser usados para navegar de una vista a otra
   */
  @Input() protected resource: HttpResource;
  /**
   * Indica si el footer de la tabla podra ser visto
   */
  @Input() showFotterButton = true;
  /**
   * Emite la fila seleccionada en la tabla
   */
  @Output() element = new EventEmitter<T>();
  /**
   * Indica que columnas de una tabla estaran ocultas
   */
  @Input() columnsToHide: string[];
  /**
   * Indica que propiedad de objeto JSON sera utilizada para mostrar los datos en una tabla
   */
  @Input() objTableDataDisplay: string;
  /**
   * Columnas a mostrar
   */
  displayedColumns: any;
  /**
   * Columnas a ocultar
   */
  columnsToDisplay: string[];

  @Output() elementAny = new EventEmitter<any>();
  /**
   * Emite el numero de filas agregadas o eliminadas en una tabla
   */
  @Input() rows: Array<DefaultColumns>;

  @Output() rowsChange = new EventEmitter<Array<DefaultColumns>>();

  /**
   * Lista de elementos que se mostraran en una talba
   */
  protected list = new Array<T>();

  dataSource = new MatTableDataSource<T>(this.list);

  @Input() showButton = true;

  @Input() mock = new Array<T>();
  @Input() simpleMock: any;
  /**
   * Muestra encabezado definido en una plantilla
   */
  @Input() showHead = true;
  constructor(private route: Router, private activatedRouter: ActivatedRoute, private service: SubResourceService<T>,
              private columns: string[]) {
    super();
    this.displayedColumns = this.columns;
    this.columnsToDisplay = this.displayedColumns.slice();

  }
  ngAfterContentInit(): void {
    if (this.columnsToHide && this.columnsToDisplay !== null && this.columnsToDisplay.length > 0) {
      this.columnsToDisplay = Smartwfm.removeItemsFromArray(this.columnsToDisplay, this.columnsToHide);

    }
  }
  create(property?: string) {
    return new Promise((resolve, reject)=>{

      if (this.mock.length > 0) {
        this.createTableMock(this.mock);
      } else {
        if (this.resource && this.resource !== null && this.resource.url && this.resource.url !== null && this.resource.url.length > 0) {
          this.service.list(this.resource.url, property, this.resource.params).subscribe(response => {
            this.dataSource.data = Smartwfm.addIndexRowTable(response);
            this.dataSource.sort = this.sort;
            this.dataSource.paginator = this.paginator;
            this.paginator._intl.itemsPerPageLabel = 'Registros por página';
            resolve();
          }, error => {
            this.dataSource.data = [];
            this.dataSource.sort = this.sort;
            this.dataSource.paginator = this.paginator;
            this.paginator._intl.itemsPerPageLabel = 'Registros por página';
            resolve();
          });

        }
      }
    })
  }
  createPost(property?: string) {
    if (this.mock.length > 0) {
      this.createTableMock(this.mock);
    } else {
      if (this.resource && this.resource !== null && this.resource.url && this.resource.url !== null && this.resource.url.length > 0) {
        this.service.postList(this.resource.url, this.resource.params, property).subscribe(response => {
          this.dataSource.data = Smartwfm.addIndexRowTable(response);
        }, error => {
          this.dataSource.data = [];
        });
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
        this.paginator._intl.itemsPerPageLabel = 'Registros por página';
      }
    }
  }
  createTableFromJsonPath(dataTable: DataTable) {
    if (!dataTable.isHaveRootArray) {
      if (dataTable.jsonPath && dataTable.jsonPath !== null) {
        this.dataSource.data = this.setExtraData(Smartwfm.getObjectProperty(dataTable.jsonPath, dataTable.obj), dataTable.obj);
      } else {
        this.dataSource.data = Smartwfm.addIndexRowTable(dataTable.obj);
      }
    } else if (dataTable.isHaveRootArray) {
      this.dataSource.data = this.createFromRootArray(dataTable);
    }
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.paginator._intl.itemsPerPageLabel = 'Registros por página';
  }
  createFromRootArray(dataTable: DataTable): Array<any> {
    const arr = new Array<any>();
    const rowIndex = 'index';
    const extra = 'extra';
    let indice = 0;
    dataTable.obj.forEach(element => {
      const objs = Smartwfm.getObjectProperty(dataTable.jsonPath, element);
      if (objs && objs !== null && objs.length > 0) {
        objs.forEach(value => {
          value[extra] = element;
          value[rowIndex] = indice;
          arr.push(value);
          indice++;
        });
      }
    });
    return arr;
  }
  private setExtraData(data: Array<any>, obj: any) {
    const rowIndex = 'index';
    const extra = 'extra';
    data.forEach((value, index) => {
      value[extra] = obj;
      value[rowIndex] = index;
    });
    return data;
  }

  createTableMock(mock: any) {
    this.dataSource.data = Smartwfm.addIndexRowTable(mock);
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.paginator._intl.itemsPerPageLabel = 'Registros por página';
  }

  protected goTo(row: T, columName: string) {
    if (this.resource && this.resource.table && this.resource.table != null && this.resource.table.length > 0) {
      const table = this.resource.table.filter(column => column.column === columName);
      if (table.length > 0) {
        RoutingUtilities.goToComponent
          (this.route, this.activatedRouter, table[0].url, Smartwfm.createUrlParams(table[0].params, row));
      }
    } else if (this.useLink) {
      this.emit(row);
    }
  }
  emit(row: T) {
    this.element.emit(row);
  }
  emitElement(data: any) {
    this.elementAny.emit(data);
  }
  navigate() {
    if (this.resource.navigate && this.resource.navigate != null) {
      const navigate = this.resource.navigate;
      if (navigate.params && navigate.params != null) {
        RoutingUtilities.goToComponent(this.route, this.activatedRouter, this.resource.navigate.nextUrl, navigate.params);
      } else {
        RoutingUtilities.goToComponentNoParams(this.route, this.activatedRouter, this.resource.navigate.nextUrl);
      }
    }
  }
  protected backPage() {
    this.back.emit(true);
  }
  addColumn(obj: any): Array<any> {
    this.dataSource.data.push(obj);
    const data = this.dataSource.data;
    this.dataSource.data = Smartwfm.addIndexRowTable(data);
    return this.dataSource.data;
  }
  removeRow(obj: any): Array<any> {
    const data = Smartwfm.removeRowTable(this.dataSource.data, obj);
    this.dataSource.data = [];
    this.dataSource.data = data;
    return this.dataSource.data;
  }
  getDate(date: number): string {
    return Smartwfm.getDateFromNumber(date);
  }

}
