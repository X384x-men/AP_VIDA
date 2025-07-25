import { AddComponent } from './add.component';
import { AddComponentDirective } from 'src/app/shared/Directives/add-component/add-component.directive';
import { ComponentFactoryResolver } from '@angular/core';

export class LoadComponent {
    public static createComponent(index: number, components: Array<AddComponent>, CFR: ComponentFactoryResolver,
                                  adHost: AddComponentDirective, componentsReferences = []): any[] {
        const references = [];
        references.push(...componentsReferences);
        const component = components[0].component;
        const componentFactory = CFR.resolveComponentFactory(component);
        const viewContainerRef = adHost.viewContainerRef;
        viewContainerRef.clear();
        const componentRef = viewContainerRef.createComponent(componentFactory);
        const currentComponent = componentRef.instance;

        currentComponent.selfRef = currentComponent;
        currentComponent.index = index;

        // prividing parent Component reference to get access to parent class methods
        currentComponent.compInteraction = this;

        // add reference for newly created component
        references.push(componentRef);
        return references;
    }
   public static remove(index: number, adHost: AddComponentDirective, componentsReferences = []): any[]  {
        let references = [];
        references.push(...componentsReferences);
        if (adHost.viewContainerRef.length < 1) {
            return;
        }
        const componentRef = references.filter(x => x.instance.index === index)[0];
        const vcrIndex = adHost.viewContainerRef.indexOf(componentRef);

        // removing component from container
        adHost.viewContainerRef.remove(vcrIndex);

        references = references.filter(x => x.instance.index !== index);
        return references;
    }
}