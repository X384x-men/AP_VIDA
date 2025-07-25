import { Component, Compiler, OnInit, HostListener } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {

  constructor(private compiler: Compiler) {
    this.compiler.clearCache();
  }

  ngOnInit(): void {
  }

  @HostListener('keypress', ['$event'])
  onKeyDown(ev: any) {
    const { key, shiftKey, ctrlKey } = ev;
    if (shiftKey && ctrlKey && key == 'V')
      alert('Despliegue: 02/03/2023');
  }

}
