import os

from github import Github
from repository import MyRepository
import pygame


# colors
RED = (250, 51, 126)
YELLOW = (255, 187, 120)
GREEN = (100, 212, 104)
GRAY = (144, 164, 174)

WINDOW_SIZE = (1300, 700)
CIRCLE_RADIUS = 50
TEXT_SIZE = 30


TOKEN = os.environ.get('TOKEN', '9050')


def main():
	update_seconds = 5

	github = Github(TOKEN)
	repository = github.get_repo(full_name_or_id=261307973)
	MyRepository.convert_super_to_sub(repository)

	clock = pygame.time.Clock()
	pygame.init()
	screen = pygame.display.set_mode(WINDOW_SIZE)
	font = pygame.font.SysFont("couriernew", TEXT_SIZE)
	done = False

	while not done:
		for event in pygame.event.get():
			if event.type == pygame.QUIT:
				done = True

		screen.fill((0, 0, 0))

		workflows = list(repository.get_workflows())
		number_workflows = len(workflows)
		for workflow_i in range(number_workflows):
			workflow = workflows[workflow_i]

			info = repository.get_workflow_run(workflow.id, branch="feature/raspberry/pipeline_integration")

			color = GRAY
			if "workflow_runs" in info and len(info["workflow_runs"]):
				conclusion = info["workflow_runs"][0]["conclusion"]
				print(workflow)
				print(conclusion)

				if conclusion == "success":
					color = GREEN
				elif conclusion == "failure":
					color = RED
				elif not conclusion:
					color = YELLOW

			circle_horizontal = int(WINDOW_SIZE[0] * (workflow_i + 1) / (number_workflows + 1))
			circle_vertical = int(WINDOW_SIZE[1] / 2)
			pygame.draw.circle(screen, color, (circle_horizontal, circle_vertical), CIRCLE_RADIUS)

			text_present = workflow.name.split(" ")
			for line_i in range(len(text_present)):
				text = font.render(text_present[line_i], True, (255, 255, 255))
				screen.blit(text, (circle_horizontal - CIRCLE_RADIUS,  circle_vertical + CIRCLE_RADIUS*2 + TEXT_SIZE*line_i))

		pygame.display.update()
		clock.tick(1/update_seconds)


if __name__ == "__main__":
	main()
