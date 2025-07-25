import { Injectable, ComponentFactoryResolver, ViewRef } from '@angular/core';
import { PostItem } from '../../class/loader-component/post-item';
import { LoaderComponet } from '../../interface/loader-component/loader-componet';
import { AddComponentDirective } from 'src/app/shared/Directives/add-component/add-component.directive';

@Injectable({
  providedIn: 'root'
})
export class LoaderComponentService {
  componentsReferences = [];
  adHost: AddComponentDirective;
  index = 0;
  constructor(private CFR: ComponentFactoryResolver) { }

  loadComponent(host: AddComponentDirective, postItem: PostItem, parent?: any): LoaderComponet {
    this.adHost = host;
    const componetFactory = this.CFR.resolveComponentFactory(postItem.component);
    this.adHost.viewContainerRef.clear();
    const componentRef = this.adHost.viewContainerRef.createComponent(componetFactory);
    const currentComponent = componentRef.instance;
    const post: LoaderComponet = currentComponent as LoaderComponet;
    post.data = postItem.data;
    post.selfRef = currentComponent;
    post.compInteraction = this;
    post.index = this.index++;
    post.directive = host;
    post.parent = parent;
    this.componentsReferences.push(componentRef);
    return post;
  }
  emmitData(host: AddComponentDirective, data: any, index: number): void {
    const componentRef = this.componentsReferences.filter(x => x.instance.index === index)[0];
    const postComponent = componentRef.instance as LoaderComponet;
  }
  /* */
  loadComponents(host: Array<AddComponentDirective>, postItems: Array<PostItem>, parent?: any): number {
    let index = -1;
    host.forEach(element => {
      this.adHost = element;
      postItems.forEach(postItem => {
        const componetFactory = this.CFR.resolveComponentFactory(postItem.component);
        this.adHost.viewContainerRef.clear();
        const componentRef = this.adHost.viewContainerRef.createComponent(componetFactory);
        const currentComponent = componentRef.instance;
        const post: LoaderComponet = currentComponent as LoaderComponet;
        post.data = postItem.data;
        post.selfRef = currentComponent;
        post.compInteraction = this;
        post.index = this.index++;
        post.directive = host;
        post.parent = parent;
        this.componentsReferences.push(componentRef);
        index = post.index;
      });
    });
    return index;
  }
  remove(index: number) {
    if (this.adHost && this.adHost !== null) {
      if (this.adHost.viewContainerRef.length < 1) {
        return;
      }
      const componentRef = this.componentsReferences.filter(x => x.instance.index === index)[0];
      if (componentRef && componentRef !== null) {
        const vcrIndex = this.adHost.viewContainerRef.indexOf(componentRef);
        this.adHost.viewContainerRef.remove(vcrIndex);
        this.componentsReferences = this.componentsReferences.filter(x => x.instance.index !== index);
      }
    }
  }
  removeAndReloadComponent(host: AddComponentDirective, postItem: PostItem, index: number, parent?: any): LoaderComponet {
    if (host && host != null && index >= 0) {
      this.remove(index);
      return this.loadComponent(host, postItem, parent);
    }
    return null;
  }
}
