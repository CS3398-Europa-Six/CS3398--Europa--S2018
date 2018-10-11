#Fernando BUuenrostro (Single line)
'''This file is to demonstrate the basic syntax of Python and serves as the
	foundation to learning pandas. Main focus is learning only syntax rather than Python specific
	utilities.  This demonstrates multiline comments'''

	#***MUST HAVE swampy installed
import swampy as sw

prompt = "Hello, World! "

print(prompt)

prompt2 = 'Goodbye, World!'

print(prompt + prompt2)

choice = input('Enter your input:')      # If you use Python 3

def polygon(t, n, length):
    angle = 360.0 / n
    polyline(t, n, length, angle)
def arc(t, r, angle):
    arc_length = 2 * math.pi * r * angle / 360
    n = int(arc_length / 3) + 1
    step_length = arc_length / n
    step_angle = float(angle) / n
    polyline(t, n, step_length, step_angle)
    
if choice == 'a':
    draw_a()
elif choice == 'b':
    draw_b()
elif choice == 'c':
    draw_c()
