import { Component, ElementRef, ViewChild } from "@angular/core";
@Component({
  selector: 'app-filter-csv',
  template: `
<label for="csvInput">Subir CSV</label>
<input type="file" #csvInput id="csvInput">
  `
})
export class FilterCSVComponent {
  @ViewChild('csvInput') inputFile: ElementRef;
  private reader: FileReader;

  constructor() {
    this.reader = new FileReader();
    this.reader.onload = () => this.filterCSV();
  }

  ngAfterViewInit() {
    this.inputFile.nativeElement.onchange = (event) => {
      this.reader.readAsText(event.target.files[0]);
      this.inputFile.nativeElement.value = null;
    };
  }

  filterCSV() {
    const text = this.reader.result.toString().trim();
    const lines = text.split('\n');
    const filterLines = lines.filter(line => line.includes('2023-03'));
    console.log(filterLines)
    this.downloadFilter(filterLines.join());
  }

  private downloadFilter(text: string): void {
    const blob = new Blob([text], {type: 'text/plain'});
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.download = 'correccionM_74_detalle_de_movimientos_  20211131_1.txt'
    link.click();
  }
}
