using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Xamarin.Forms;

namespace Etherial
{
    public class Table
    {
        const int rows = 3;
        const int columns = 3;
        public Color[,] board = new Color[rows, columns] {
            { Color.Black, Color.Black, Color.Black},
            { Color.Black, Color.Black, Color.Black},
            { Color.Black, Color.Black, Color.Black}
        };
    }


    public class GameView : ContentView
    {
        private readonly Table table;
        public static Color[] Colors = {
            Color.Pink,
            Color.Purple,
            Color.Yellow
        };
        const int rows = 3;
        const int columns = 3;
        public static int Score { get; set; }

        public static Label label = new Label() { Text = $"Score: {Score}", HorizontalOptions = LayoutOptions.Center, FontSize = 25 };

        public static Button switcher = new Button()
        { BackgroundColor = GerenateRandomColor(), Padding = 50, WidthRequest = 50, HeightRequest = 50 };

        public static Grid grid = new Grid() { Padding = 25 };

        static int[,] winX = new int[6, 3]
        {
            {0,0,0},
            {1,1,1},
            {2,2,2},
            {0,1,2},
            {0,1,2},
            {0,1,2}
        };
        static int[,] winY = new int[6, 3]
        {
            {0,1,2 },
            {0,1,2 },
            {0,1,2 },
            {0,0,0 },
            {1,1,1 },
            {2,2,2 }
        };
        public void Make(object sender)
        {
            int counter = 0;
            Button theButton = (Button)sender;

            int indexRow = Grid.GetRow(theButton);
            int indexColumn = Grid.GetColumn(theButton);

            if (theButton.BackgroundColor != Color.Black)
            {
                return;
            }

            table.board[indexRow, indexColumn] = switcher.BackgroundColor;
            theButton.BackgroundColor = switcher.BackgroundColor;

            switcher.BackgroundColor = GerenateRandomColor();
            for (int i = 0; i < winX.GetLength(0); i++)
            {

                for (int j = 0; j < winX.GetLength(1); j++)
                {
                    int X = winX[i, j];
                    int Y = winY[i, j];
                    if (table.board[X, Y] != Color.Black && table.board[X, Y] == theButton.BackgroundColor)
                    {
                        counter++;
                    }
                }

                if (counter == 3)
                {
                    Score += 1;
                    label.Text = $"Score: {Score}";
                    ClearWinCombination(i);
                    theButton.BackgroundColor = Color.Black;
                    break;
                }

                counter = 0;
            }

            if (!table.board.Cast<Color>().Contains(Color.Black))
            {
                Navigation.PushModalAsync(new GameOverPage(this));
            }

        }
        private void ClearWinCombination(int nCombination)
        {
            for (int column = 0; column < columns; column++)
            {
                int cellX = winX[nCombination, column];
                int cellY = winY[nCombination, column];
                table.board[cellX, cellY] = Color.Black;
                grid.Children.Add(GenerateButton(), cellY, cellX);
            }
        }
        Button GenerateButton()
        {
            Button result = new Button() { BackgroundColor = Color.Black };
            result.Clicked += (sender, args) => Make(sender);
            return result;
        }
        static Color GerenateRandomColor()
        {
            Random random = new Random();
            int index = random.Next(0, 3);
            return Colors[index];
        }
        public void Restart()
        {
            for (int i = 0; i < rows; i++)
            {
                for (int j = 0; j < columns; j++)
                {
                    grid.Children.Add(GenerateButton(), i, j);
                    table.board[i, j] = Color.Black;
                }
            }
            Score = 0;
            label.Text = $"Score: {Score}";

        }

        public GameView()
        {

            table = new Table();


            for (int i = 0; i < rows; i++)
            {
                grid.RowDefinitions.Add(new RowDefinition() { Height = new GridLength(45) });
            }

            for (int i = 0; i < columns; i++)
            {
                grid.ColumnDefinitions.Add(new ColumnDefinition() { Width = new GridLength(45) });
            }

            for (int i = 0; i < rows; i++)
            {
                for (int j = 0; j < columns; j++)
                {
                    grid.Children.Add(GenerateButton(), i, j);
                }
            }

            Button restart = new Button() {Text="Restart", FontSize=25};
            restart.Clicked += (sender, args) => Restart();
            restart.HorizontalOptions = LayoutOptions.Center;
            restart.VerticalOptions = LayoutOptions.End;
           
            grid.HorizontalOptions = LayoutOptions.Center;
            switcher.HorizontalOptions = LayoutOptions.Center;
            
            Content = new StackLayout
            {
                Margin = new Thickness(0, 50, 0, 0),
                Children = {
                        label,
                        switcher,
                        grid,
                        restart
                    }

            };



        }
    }
}