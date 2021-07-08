import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProjectActivityMaster, ProjectActivityMaster } from '../project-activity-master.model';
import { ProjectActivityMasterService } from '../service/project-activity-master.service';

@Injectable({ providedIn: 'root' })
export class ProjectActivityMasterRoutingResolveService implements Resolve<IProjectActivityMaster> {
  constructor(protected service: ProjectActivityMasterService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProjectActivityMaster> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((projectActivityMaster: HttpResponse<ProjectActivityMaster>) => {
          if (projectActivityMaster.body) {
            return of(projectActivityMaster.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProjectActivityMaster());
  }
}
