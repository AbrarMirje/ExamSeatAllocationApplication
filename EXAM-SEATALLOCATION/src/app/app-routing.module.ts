import { RouterModule, Routes } from '@angular/router';
import { GoogleFormComponent } from './modules/components/hall-ticket-form/google-form.component';
import { HallTicketComponent } from './modules/components/hall-ticket/hall-ticket.component';
import { AdminComponent } from './modules/components/admin/admin.component';
import { DashboardComponent } from './modules/components/dashboard/dashboard.component';
import { HomeComponent } from './modules/components/home/home.component';
import { StudentFormComponent } from './modules/components/student-form/student-form.component';
import { AddBlockComponent } from './modules/components/add-block/add-block.component';
import { AddCoursesComponent } from './modules/components/add-courses/add-courses.component';
import { AddSupervisorComponent } from './modules/components/add-supervisor/add-supervisor.component';
import { ShowStudentinfoComponent } from './modules/components/show-studentinfo/show-studentinfo.component';
import { NgModule } from '@angular/core';
import { AdminHomeComponent } from './modules/components/admin-home/admin-home.component';

const routes: Routes = [
  // Redirect root to 'admin'
  { path: '', redirectTo: '/admin', pathMatch: 'full' },

  // Admin route
  { path: 'admin', component: AdminComponent },

  // Dashboard route
  {
    path: 'dashboard',
    component: DashboardComponent,
    children: [
      { path: '', component: AdminHomeComponent },
      { path: 'home', component: HomeComponent },
      { path: 'student-info', component: StudentFormComponent },
      { path: 'add-block', component: AddBlockComponent },
      { path: 'add-courses', component: AddCoursesComponent },
      { path: 'add-supervisor', component: AddSupervisorComponent },
      { path: 'show-studentinfo', component: ShowStudentinfoComponent },
    ],
  },

  // Other standalone routes
  { path: 'google-form', component: GoogleFormComponent },
  { path: 'hall-ticket', component: HallTicketComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
