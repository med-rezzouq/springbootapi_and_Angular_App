import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminGuard } from '../guards/authAdmin/admin.guard';
import { AddProductComponent } from './components/add-product/add-product.component';
import { AllOrdersComponent } from './components/all-orders/all-orders.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { UpdateProductComponent } from './components/update-product/update-product.component';
import { UsersComponent } from './components/users/users.component';

const routes: Routes = [

  { path: 'dashboard', component: DashboardComponent, canActivate: [AdminGuard] },
  { path: 'product', component: AddProductComponent, canActivate: [AdminGuard] },
  { path: 'product/:productId', component: UpdateProductComponent, canActivate: [AdminGuard] },
  { path: 'users', component: UsersComponent, canActivate: [AdminGuard] },
  { path: 'orders', component: AllOrdersComponent, canActivate: [AdminGuard] },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
