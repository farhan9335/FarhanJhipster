import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProjectActivityMaster } from '../project-activity-master.model';
import { ProjectActivityMasterService } from '../service/project-activity-master.service';
import { ProjectActivityMasterDeleteDialogComponent } from '../delete/project-activity-master-delete-dialog.component';

@Component({
  selector: 'jhi-project-activity-master',
  templateUrl: './project-activity-master.component.html',
})
export class ProjectActivityMasterComponent implements OnInit {
  projectActivityMasters?: IProjectActivityMaster[];
  isLoading = false;

  constructor(protected projectActivityMasterService: ProjectActivityMasterService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.projectActivityMasterService.query().subscribe(
      (res: HttpResponse<IProjectActivityMaster[]>) => {
        this.isLoading = false;
        this.projectActivityMasters = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IProjectActivityMaster): number {
    return item.id!;
  }

  delete(projectActivityMaster: IProjectActivityMaster): void {
    const modalRef = this.modalService.open(ProjectActivityMasterDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.projectActivityMaster = projectActivityMaster;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
