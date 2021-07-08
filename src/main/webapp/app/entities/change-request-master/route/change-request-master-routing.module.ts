import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ChangeRequestMasterComponent } from '../list/change-request-master.component';
import { ChangeRequestMasterDetailComponent } from '../detail/change-request-master-detail.component';
import { ChangeRequestMasterUpdateComponent } from '../update/change-request-master-update.component';
import { ChangeRequestMasterRoutingResolveService } from './change-request-master-routing-resolve.service';

const changeRequestMasterRoute: Routes = [
  {
    path: '',
    component: ChangeRequestMasterComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ChangeRequestMasterDetailComponent,
    resolve: {
      changeRequestMaster: ChangeRequestMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ChangeRequestMasterUpdateComponent,
    resolve: {
      changeRequestMaster: ChangeRequestMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ChangeRequestMasterUpdateComponent,
    resolve: {
      changeRequestMaster: ChangeRequestMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(changeRequestMasterRoute)],
  exports: [RouterModule],
})
export class ChangeRequestMasterRoutingModule {}
