from github import Repository


class MyRepository(Repository.Repository):
	def __init__(self):
		super().__init__()

	def get_workflow_run(self, workflow_id, branch="master"):
		headers, data = self._requester.requestJsonAndCheck(
			"GET", f"{self.url}/actions/workflows/{workflow_id}/runs?branch={branch}",
		)

		return data

	@staticmethod
	def convert_super_to_sub(super_object):
		super_object.__class__ = MyRepository
