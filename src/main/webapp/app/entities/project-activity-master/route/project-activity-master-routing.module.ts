import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProjectActivityMasterComponent } from '../list/project-activity-master.component';
import { ProjectActivityMasterDetailComponent } from '../detail/project-activity-master-detail.component';
import { ProjectActivityMasterUpdateComponent } from '../update/project-activity-master-update.component';
import { ProjectActivityMasterRoutingResolveService } from './project-activity-master-routing-resolve.service';

const projectActivityMasterRoute: Routes = [
  {
    path: '',
    component: ProjectActivityMasterComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProjectActivityMasterDetailComponent,
    resolve: {
      projectActivityMaster: ProjectActivityMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProjectActivityMasterUpdateComponent,
    resolve: {
      projectActivityMaster: ProjectActivityMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProjectActivityMasterUpdateComponent,
    resolve: {
      projectActivityMaster: ProjectActivityMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(projectActivityMasterRoute)],
  exports: [RouterModule],
})
export class ProjectActivityMasterRoutingModule {}
