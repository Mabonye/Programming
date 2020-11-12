import time
import random


def print_sleep(my_print):
    print(my_print)
    time.sleep(2)


def intro(animal):
    print_sleep("You find yourself standing in an open field, filled with"
                " grass and yellow wildflowers.")
    print_sleep(f"Rumor has it that a {animal} is somewhere around here, and"
                " has been terrifying the nearby village.")
    print_sleep("In front of you is a house.")
    print_sleep("To your right is a dark cave.")
    print_sleep("In your hand you hold your trusty (but not very effective)"
                " dagger.")


def house(response, animal, weapons):
    if response == "1":
        print_sleep("You approach the door of the house.")
        print_sleep("You are about to knock when the door opens and "
                    f"out steps a {animal}.")
        print_sleep(f"Eep! This is the {animal}'s house!")
        print_sleep(f"The {animal} attacks you!")
        if animal == "pirate" or animal == "troll" or animal == "dragon":
            print_sleep("You feel a bit under-prepared for this, what with"
                        " only having a tiny dagger.")

        fight(animal, weapons)


def cave(response, animal, weapons):
    if response == "2":
        print_sleep("You peer cautiously into the cave.")
        if not weapons:
            print_sleep("It turns out to be only a very small cave.")
            print_sleep("Your eye catches a glint of metal behind a rock.")
            print_sleep("You have found the magical Sword of Ogoroth!")
            print_sleep("You discard your silly old dagger and take"
                        " the sword with you.")
            weapons.append("magical sword")

        else:
            print_sleep("You've been here before, and gotten all the"
                        " good stuff. It's "
                        "just an empty cave now.")
        print_sleep("You walk back out to the field.")
        play_game(animal, weapons)


def fight(animal, weapons):
    decision = input("Would you like to (1) fight or (2) run away?")
    if decision == "2":
        print_sleep("You run back into the field. Luckily, you don't"
                    " seem to have been followed.")
        play_game(animal, weapons)

    else:
        if decision == "1":
            print_sleep("You do your best...")
            print_sleep(f"but your dagger is no match for the {animal}.")
            print_sleep("You have been defeated!")
        play_again()


def play_again():
    output = input("Would you like to play again? (y/n)").lower()
    if output == "y":
        print_sleep("Excellent! Restarting the game ...")
        main()
    elif output == "n":
        print_sleep("Thanks for playing! See you next time.")
    else:
        play_again()


def choice(response, animal, weapons):
    if response == "1":
        house(response, animal, weapons)

    elif response == "2":
        cave(response, animal, weapons)

    else:
        response = input("Please enter 1 or 2.\n")
        choice(response, animal, weapons)


def play_game(animal, weapons):
    print_sleep("\nEnter 1 to knock on the door of the house.")
    print_sleep("Enter 2 to peer into the cave.")
    response = input("What would you like to do?\n"
                     "Please enter 1 or 2).\n")
    choice(response, animal, weapons)


def main():
    creature = ["gorgon", "pirate", "wicked fairie", "troll", "dragon"]
    animal = random.choice(creature)
    weapons = []
    intro(animal)
    play_game(animal, weapons)

main()
