import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IActivityMaster, ActivityMaster } from '../activity-master.model';
import { ActivityMasterService } from '../service/activity-master.service';

@Injectable({ providedIn: 'root' })
export class ActivityMasterRoutingResolveService implements Resolve<IActivityMaster> {
  constructor(protected service: ActivityMasterService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IActivityMaster> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((activityMaster: HttpResponse<ActivityMaster>) => {
          if (activityMaster.body) {
            return of(activityMaster.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ActivityMaster());
  }
}
