<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- Edit Modal HTML -->
		<div id="editEmployeeModal" class="modal fade">
			<div class="modal-dialog">
				<div class="modal-content">
					<form action="/EmployeeCheck/update" method="post">
						<div class="modal-header">
							<h4 class="modal-title">Edit Employee</h4>
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
						</div>
						<div class="modal-body">
							<div class="form-group">
								<label>Name</label> 
								<input type="text" ng-model="employee.name" class="form-control" name="name" required>
							</div>
							<div class="form-group">
								<label>Email</label> <input type="email" ng-model="employee.email"   name="email" class="form-control"
									required>
							</div>
							<div class="form-group">
								<label>Address</label>
								<textarea class="form-control" name="address" ng-model="employee.address"   required></textarea>
							</div>
							<div class="form-group">
								<label>Phone</label> <input type="text"  name="phone" ng-model="employee.phone"  class="form-control"
									required>
							</div>
						</div>
						
						<div class="modal-footer">
							<input type="hidden" name="id" value="{{employee.id}}"/>
							<input type="button" class="btn btn-default" data-dismiss="modal"
								value="Cancel"> <input type="submit" class="btn btn-info"
								value="Save">
						</div>
					</form>
				</div>
			</div>
		</div>