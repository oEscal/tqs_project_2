import argparse
import os

from github import Github
from repository import MyRepository
import pygame


# colors
RED = (250, 51, 126)
YELLOW = (255, 187, 120)
GREEN = (100, 212, 104)
GRAY = (144, 164, 174)
WHITE = (255, 255, 255)

WINDOW_SIZE = (1300, 700)
CIRCLE_RADIUS = 50
TEXT_SIZE = 30


TOKEN = os.environ.get('TOKEN', '9050')


def main(branch, update_seconds):
	# update_seconds = 5
	# branch = "feature/raspberry/pipeline_integration"

	# github info
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

		last_commit = repository.get_branch(branch).commit

		workflows = list(repository.get_workflows())
		number_workflows = len(workflows)
		for workflow_i in range(number_workflows):
			workflow = workflows[workflow_i]

			info = repository.get_workflow_run(workflow.id, branch=branch)

			color = GRAY
			if "workflow_runs" in info and len(info["workflow_runs"]):
				conclusion = info["workflow_runs"][0]["conclusion"]

				if conclusion == "success":
					color = GREEN
				elif conclusion == "failure":
					color = RED
				elif not conclusion:
					color = YELLOW

			# branch info
			text = font.render(f"Branch: {branch}", True, WHITE)
			screen.blit(text, (10, 10))
			text = font.render(f"Commit message: {last_commit.commit.message}", True, WHITE)
			screen.blit(text, (10, 10 + TEXT_SIZE))
			text = font.render(f"Commit author: {last_commit.commit.author.name}", True, WHITE)
			screen.blit(text, (10, 10 + TEXT_SIZE*2))

			# circles
			circle_horizontal = int(WINDOW_SIZE[0] * (workflow_i + 1) / (number_workflows + 1))
			circle_vertical = int(WINDOW_SIZE[1] / 2)
			pygame.draw.circle(screen, color, (circle_horizontal, circle_vertical), CIRCLE_RADIUS)

			text_present = workflow.name.split(" ")
			for line_i in range(len(text_present)):
				text = font.render(text_present[line_i], True, WHITE)
				screen.blit(text, (circle_horizontal - CIRCLE_RADIUS,  circle_vertical + CIRCLE_RADIUS*2 + TEXT_SIZE*line_i))

		pygame.display.update()
		clock.tick(1/update_seconds)


if __name__ == "__main__":
	parser = argparse.ArgumentParser()
	parser.add_argument('--branch', type=str, default="master")
	parser.add_argument('--update_seconds', type=int, default=5)
	args = parser.parse_args()

	main(args.branch, args.update_seconds)
