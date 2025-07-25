import { Directive, Input, ElementRef, Renderer2, OnInit, SimpleChanges, OnChanges } from '@angular/core';
const ADD = 'A';
const DELETE = 'E';
const UPDATE = 'U';
@Directive({
  selector: '[appChangeButton]'
})
export class ChangeButtonDirective implements OnInit, OnChanges {
  @Input('appChangeButton') options: { opt?: string, disabled?: boolean, btnName?: string };
  constructor(private el: ElementRef, private renderer: Renderer2) { }
  ngOnInit() {
    this.renderer.setStyle(this.el.nativeElement, 'color', 'primary');
  }
  ngOnChanges(changes: SimpleChanges): void {
    this.renderer.setStyle(this.el.nativeElement, 'color', 'white');
    const hasBtnName = this.options.btnName && this.options.btnName !== null && this.options.btnName.length > 0;
    this.renderer.addClass(this.el.nativeElement, 'btn');
    if (this.options.opt && !this.options.disabled) {
      if (this.options.opt === DELETE) {
        this.el.nativeElement.innerText = hasBtnName ? this.options.btnName : 'Eliminar';
        this.renderer.addClass(this.el.nativeElement, 'btn-danger');
        this.renderer.setProperty(this.el.nativeElement, 'id', 'el');
      } else if (this.options.opt === ADD) {
        this.el.nativeElement.innerText = hasBtnName ? this.options.btnName : 'Agregar';
        this.renderer.addClass(this.el.nativeElement, 'btn-success');
      } else if (this.options.opt === UPDATE) {
        this.el.nativeElement.innerText = hasBtnName ? this.options.btnName : 'Actualizar';
        this.renderer.setStyle(this.el.nativeElement, 'pointer-events', 'none');
        this.renderer.setStyle(this.el.nativeElement, 'background-color', '#54a7d2');
      }
    } else if (this.options.btnName && this.options.btnName !== null && this.options.btnName.length > 0) {
      this.el.nativeElement.innerText = this.options.btnName;
    }
    if (this.options.disabled) {
      this.renderer.setStyle(this.el.nativeElement, 'color', 'black');
      this.renderer.setStyle(this.el.nativeElement, 'pointer-events', 'none');
      this.renderer.setStyle(this.el.nativeElement, 'background-color', '#9e9e9e59');
      // this.renderer.setStyle(this.el.nativeElement, 'opacity', '0.8');
    } else {
      this.renderer.setStyle(this.el.nativeElement, 'pointer-events', 'all');
    }
  }

}
