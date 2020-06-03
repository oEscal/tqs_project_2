import os

from github import Github
from repository import MyRepository


TOKEN = os.environ.get('TOKEN', '9050')
g = Github(TOKEN)

repository = g.get_repo(full_name_or_id=261307973)
MyRepository.convert_super_to_sub(repository)

for workflow in repository.get_workflows():
	print(workflow)

	info = repository.get_workflow_run(workflow.id)
	if "workflow_runs" in info:
		print(info["workflow_runs"][0]["conclusion"])
