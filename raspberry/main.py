import os

from github import Github
from repository import MyRepository
import pygame


# colors
RED = (250, 51, 126)
YELLOW = (255, 187, 120)
GREEN = (100, 212, 104)

WINDOW_SIZE = (1080, 700)


TOKEN = os.environ.get('TOKEN', '9050')


def main():
	update_seconds = 5

	github = Github(TOKEN)
	repository = github.get_repo(full_name_or_id=261307973)
	MyRepository.convert_super_to_sub(repository)

	clock = pygame.time.Clock()
	pygame.init()
	screen = pygame.display.set_mode(WINDOW_SIZE)
	done = False

	while not done:
		for event in pygame.event.get():
			if event.type == pygame.QUIT:
				done = True

		screen.fill((0, 0, 0))

		workflow_i = 0
		for workflow in repository.get_workflows():

			info = repository.get_workflow_run(workflow.id, branch="feature/raspberry/pipeline_integration")

			if "workflow_runs" in info and len(info["workflow_runs"]):
				conclusion = info["workflow_runs"][0]["conclusion"]
				print(workflow)
				print(conclusion)

				color = (255, 255, 255)
				if conclusion == "success":
					color = GREEN
				elif conclusion == "failure":
					color = RED
				elif not conclusion:
					color = YELLOW

				# TODO -> mudar para dar para qualquer numero de pipelines
				circle_height = int(WINDOW_SIZE[0] * (workflow_i + 1) / (4 + 1))
				pygame.draw.circle(screen, color, (circle_height, int(WINDOW_SIZE[1] / 2)), 50)

				workflow_i += 1

		pygame.display.update()
		clock.tick(1/update_seconds)


if __name__ == "__main__":
	main()
