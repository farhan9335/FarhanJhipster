import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ChangeRequestMasterComponent } from './list/change-request-master.component';
import { ChangeRequestMasterDetailComponent } from './detail/change-request-master-detail.component';
import { ChangeRequestMasterUpdateComponent } from './update/change-request-master-update.component';
import { ChangeRequestMasterDeleteDialogComponent } from './delete/change-request-master-delete-dialog.component';
import { ChangeRequestMasterRoutingModule } from './route/change-request-master-routing.module';

@NgModule({
  imports: [SharedModule, ChangeRequestMasterRoutingModule],
  declarations: [
    ChangeRequestMasterComponent,
    ChangeRequestMasterDetailComponent,
    ChangeRequestMasterUpdateComponent,
    ChangeRequestMasterDeleteDialogComponent,
  ],
  entryComponents: [ChangeRequestMasterDeleteDialogComponent],
})
export class ChangeRequestMasterModule {}
