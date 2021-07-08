import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProjectActivityMasterComponent } from './list/project-activity-master.component';
import { ProjectActivityMasterDetailComponent } from './detail/project-activity-master-detail.component';
import { ProjectActivityMasterUpdateComponent } from './update/project-activity-master-update.component';
import { ProjectActivityMasterDeleteDialogComponent } from './delete/project-activity-master-delete-dialog.component';
import { ProjectActivityMasterRoutingModule } from './route/project-activity-master-routing.module';

@NgModule({
  imports: [SharedModule, ProjectActivityMasterRoutingModule],
  declarations: [
    ProjectActivityMasterComponent,
    ProjectActivityMasterDetailComponent,
    ProjectActivityMasterUpdateComponent,
    ProjectActivityMasterDeleteDialogComponent,
  ],
  entryComponents: [ProjectActivityMasterDeleteDialogComponent],
})
export class ProjectActivityMasterModule {}
