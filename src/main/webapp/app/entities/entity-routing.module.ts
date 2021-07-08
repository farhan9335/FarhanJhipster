import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'manager',
        data: { pageTitle: 'hclMonitorToolApp.manager.home.title' },
        loadChildren: () => import('./manager/manager.module').then(m => m.ManagerModule),
      },
      {
        path: 'employee',
        data: { pageTitle: 'hclMonitorToolApp.employee.home.title' },
        loadChildren: () => import('./employee/employee.module').then(m => m.EmployeeModule),
      },
      {
        path: 'project',
        data: { pageTitle: 'hclMonitorToolApp.project.home.title' },
        loadChildren: () => import('./project/project.module').then(m => m.ProjectModule),
      },
      {
        path: 'project-activity-master',
        data: { pageTitle: 'hclMonitorToolApp.projectActivityMaster.home.title' },
        loadChildren: () => import('./project-activity-master/project-activity-master.module').then(m => m.ProjectActivityMasterModule),
      },
      {
        path: 'change-request-master',
        data: { pageTitle: 'hclMonitorToolApp.changeRequestMaster.home.title' },
        loadChildren: () => import('./change-request-master/change-request-master.module').then(m => m.ChangeRequestMasterModule),
      },
      {
        path: 'activity-master',
        data: { pageTitle: 'hclMonitorToolApp.activityMaster.home.title' },
        loadChildren: () => import('./activity-master/activity-master.module').then(m => m.ActivityMasterModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
