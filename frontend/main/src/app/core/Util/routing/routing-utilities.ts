import { ActivatedRoute, Router, ParamMap, UrlSegmentGroup, PRIMARY_OUTLET, UrlSegment } from '@angular/router';
import { Observable } from 'rxjs';
import { Menu, Options } from '../../interface/menu/mat-panel-menu';
import { Smartwfm } from '../smartwfm/smartwfm';

export class RoutingUtilities {
  //Trae el parametro de URL
  public static getParamsFromUrl(route: ActivatedRoute, data: string, isPathVariable?: boolean) {
    let value: string;
    if (isPathVariable) {
      route.params.subscribe(params => value = params[data]);
    } else {
      route.queryParamMap.subscribe(params => {
        value = params.get(data);
      });
    }
    return value;
  }
  private static getParamFromUrl(route: ActivatedRoute, data: string): Observable<ParamMap> {
    return route.queryParamMap;
  }
  public static getJsonFromUrl(route: ActivatedRoute, data: string[]): Promise<any> {
    const json: any = {};
    let isPresent = false;
    data.forEach((element, index) => {
      const key = data[index];
      let value = '';
      this.getParamFromUrl(route, element).subscribe(datas => { value = datas.get(key); });
      if (value && value.length > 0) {
        json[key] = value.replace('_', ' ');
        isPresent = true;
      }
    });
    if (isPresent) {
      return new Promise(resolve => resolve(json));
    }
    return new Promise(resolve => resolve(undefined));
  }
  public static goToComponent(router: Router, activatedRoute: ActivatedRoute, URL: string, params: any) {
    router.navigate([URL], { queryParams: params, relativeTo: activatedRoute });
  }
  public static goToComponentNoParams(router: Router, activatedRoute: ActivatedRoute, URL: string) {
    router.navigate([URL], { relativeTo: activatedRoute });
  }
  public static getParamsFromStringUrl(data: Array<Menu>, urlPath: string, paramsPath: string, router: Router) {
    data.forEach((element: Menu) => {
      if (element.subMenu && element.subMenu != null && element.subMenu.length > 0) {
        element.subMenu.forEach((subMenu: Options) => {
          subMenu[paramsPath] = {};
          const url = this.urlDecoder(subMenu[urlPath], router);
          const params = this.urlParams(subMenu[urlPath], router);
          Smartwfm.setObjectProperty(urlPath, subMenu, url);
          Smartwfm.setObjectProperty(paramsPath, subMenu, params);
        });
      }
    });
  }
  public static urlDecoder(url: string, router: Router): string {
    const parsedUrl = router.parseUrl(url);
    const g: UrlSegmentGroup = parsedUrl.root.children[PRIMARY_OUTLET];
    const s: UrlSegment[] = g.segments;
    return `${s.map(p => p.path).join('/')}`;
  }
  public static urlParams(url: string, router: Router): any {
    const parsedUrl = router.parseUrl(url);
    return parsedUrl.queryParams;
  }
}
