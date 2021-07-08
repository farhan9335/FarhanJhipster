import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IChangeRequestMaster, ChangeRequestMaster } from '../change-request-master.model';
import { ChangeRequestMasterService } from '../service/change-request-master.service';

@Injectable({ providedIn: 'root' })
export class ChangeRequestMasterRoutingResolveService implements Resolve<IChangeRequestMaster> {
  constructor(protected service: ChangeRequestMasterService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IChangeRequestMaster> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((changeRequestMaster: HttpResponse<ChangeRequestMaster>) => {
          if (changeRequestMaster.body) {
            return of(changeRequestMaster.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ChangeRequestMaster());
  }
}
