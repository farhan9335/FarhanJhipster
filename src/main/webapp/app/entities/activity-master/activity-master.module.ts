import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ActivityMasterComponent } from './list/activity-master.component';
import { ActivityMasterDetailComponent } from './detail/activity-master-detail.component';
import { ActivityMasterUpdateComponent } from './update/activity-master-update.component';
import { ActivityMasterDeleteDialogComponent } from './delete/activity-master-delete-dialog.component';
import { ActivityMasterRoutingModule } from './route/activity-master-routing.module';

@NgModule({
  imports: [SharedModule, ActivityMasterRoutingModule],
  declarations: [
    ActivityMasterComponent,
    ActivityMasterDetailComponent,
    ActivityMasterUpdateComponent,
    ActivityMasterDeleteDialogComponent,
  ],
  entryComponents: [ActivityMasterDeleteDialogComponent],
})
export class ActivityMasterModule {}
