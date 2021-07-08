import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ActivityMasterComponent } from '../list/activity-master.component';
import { ActivityMasterDetailComponent } from '../detail/activity-master-detail.component';
import { ActivityMasterUpdateComponent } from '../update/activity-master-update.component';
import { ActivityMasterRoutingResolveService } from './activity-master-routing-resolve.service';

const activityMasterRoute: Routes = [
  {
    path: '',
    component: ActivityMasterComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ActivityMasterDetailComponent,
    resolve: {
      activityMaster: ActivityMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ActivityMasterUpdateComponent,
    resolve: {
      activityMaster: ActivityMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ActivityMasterUpdateComponent,
    resolve: {
      activityMaster: ActivityMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(activityMasterRoute)],
  exports: [RouterModule],
})
export class ActivityMasterRoutingModule {}
