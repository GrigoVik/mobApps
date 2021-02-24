using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace Etherial
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class GameOverPage : ContentPage
    {
        GameView GameView;
        public GameOverPage(GameView gameView)
        {
            Title = "Game Over!";
            GameView = gameView;
            Button backButton = new Button
            {
                Text = "Restart",
                HorizontalOptions = LayoutOptions.Center,
                VerticalOptions = LayoutOptions.Center
            };
            backButton.Clicked += Restart_Click;
            Content = backButton;
        }
        private async void Restart_Click(object sender, EventArgs e)
        {
            GameView.Restart();
            await Navigation.PopModalAsync();
        }
    }
}