import pygame
import random
import time

# Initialize pygame
pygame.init()

# Set up display
width, height = 800, 600
window = pygame.display.set_mode((width, height))
pygame.display.set_caption('Typing Game')

# Set up fonts
font = pygame.font.Font(None, 74)

# Define the keys
keys = ['q', 'w', 'e', 'r', '1', '2', '3', '4', 'a', 's', 'd', 'f']

# Function to draw text on screen
def draw_text(text, font, color, surface, x, y):
    textobj = font.render(text, True, color)
    textrect = textobj.get_rect()
    textrect.center = (x, y)
    surface.blit(textobj, textrect)

# Map keys to pygame key constants
key_map = {
    'q': pygame.K_q,
    'w': pygame.K_w,
    'e': pygame.K_e,
    'r': pygame.K_r,
    '1': pygame.K_1,
    '2': pygame.K_2,
    '3': pygame.K_3,
    '4': pygame.K_4,
    'a': pygame.K_a,
    's': pygame.K_s,
    'd': pygame.K_d,
    'f': pygame.K_f
}

# Game variables
current_key = random.choice(keys)
start_time = time.time()
key_times = []
total_keys = 300
key_count = 0
incorrect_key_presses = 0
correct_press_count = 0  # To track consecutive correct presses

# Main game loop
running = True
while running and key_count < total_keys:
    window.fill((0, 0, 0))  # Fill the screen with black
    draw_text(current_key, font, (255, 255, 255), window, width / 2, height / 2)
    
    pygame.display.flip()
    
    start_key_time = time.time()
    key_pressed = False
    
    while not key_pressed:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                running = False
                key_pressed = True  # Ensure to break out of the loop
            if event.type == pygame.KEYDOWN:
                if event.key == key_map[current_key]:
                    correct_press_count += 1
                    if correct_press_count == 2:
                        key_pressed = True
                        key_time = time.time() - start_key_time
                        key_times.append(key_time)
                        key_count += 1
                        current_key = random.choice(keys)
                        correct_press_count = 0  # Reset for the next key
                else:
                    incorrect_key_presses += 1
                    correct_press_count = 0  # Reset on incorrect press
                break

# Calculate the average time
average_time = sum(key_times) / len(key_times) if key_times else 0

# Display the result
window.fill((0, 0, 0))
draw_text(f'Average Time: {average_time:.2f} seconds', font, (255, 255, 255), window, width / 2, height / 2 - 40)
draw_text(f'Incorrect Presses: {incorrect_key_presses}', font, (255, 255, 255), window, width / 2, height / 2 + 40)
pygame.display.flip()

# Wait for a few seconds before closing the game
time.sleep(5)
pygame.quit()
