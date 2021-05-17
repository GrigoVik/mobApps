//
//  ViewController.swift
//  lab5
//
//  Created by Harbros60 on 17.05.21.
//

import UIKit

private enum Colors: CaseIterable {
    case red
    case green
    case blue
    
    static func random() -> UIColor {
        switch Colors.allCases.randomElement() {
        case .red:
            return UIColor.red
        case .blue:
            return UIColor.blue
        case .green:
            return UIColor.green
        case .none:
            return UIColor()
        }
    }
}

private enum Constants {
    static let inset100: CGFloat = 100
    static let inset10: CGFloat = 10
    static let inset20: CGFloat = 20
    static let inset50: CGFloat = 50
}

class ViewController: UIViewController {

    private let mainStackView: UIStackView = {
        let stackView = UIStackView()
        stackView.axis = .vertical
        stackView.spacing = 10
        stackView.distribution = .fillEqually
        return stackView
    }()
    private var buttons = [UIButton]()
    
    private let rect: UIButton = {
        let rect = UIButton()
        rect.backgroundColor = .gray
        rect.layer.cornerRadius = 8
        rect.addTarget(self, action: #selector(help), for: .touchUpOutside)
        rect.setTitle("Help", for: .normal)
        rect.setTitleColor(.black, for: .normal)
        return rect
    }()
    
    private var score = 0 {
        didSet {
            label.text = "Your Score: \(score)"
        }
    }
    
    private let resetButton: UIButton = {
        let button = UIButton()
        button.addTarget(self, action: #selector(resetGame), for: .touchUpInside)
        button.setTitle("New Game", for: .normal)
        button.setTitleColor(.black, for: .normal)
        return button
    }()
    
    private let label: UILabel = {
        let label = UILabel()
        label.textAlignment = .center
        label.font = UIFont.boldSystemFont(ofSize: 16)
        label.text = "Your score: 0"
        label.textAlignment = .left
        return label
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        view.addSubview(mainStackView)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        createDesk()
        createConstraints()
        rect.backgroundColor = Colors.random()
    }
    
    private func createDesk() {
        (0..<3).forEach { _ in
            let stackView = UIStackView()
            stackView.axis = .horizontal
            stackView.spacing = 10
            stackView.distribution = .fillEqually
            (0..<3).forEach { _ in
                let button = UIButton()
                button.backgroundColor = .gray
                button.layer.cornerRadius = 8
                button.addTarget(self, action: #selector(changeColor(sender:)), for: .touchUpInside)
                stackView.addArrangedSubview(button)
                buttons.append(button)
            }
            mainStackView.addArrangedSubview(stackView)
        }
        view.addSubview(rect)
        view.addSubview(label)
        view.addSubview(resetButton)
    }
    
    private func createConstraints() {
        mainStackView.translatesAutoresizingMaskIntoConstraints = false
        mainStackView.topAnchor.constraint(equalTo: view.topAnchor, constant: UIScreen.main.bounds.height / 2).isActive = true
        mainStackView.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: Constants.inset10).isActive = true
        mainStackView.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -Constants.inset10).isActive = true
        mainStackView.bottomAnchor.constraint(equalTo: view.bottomAnchor, constant: -Constants.inset20).isActive = true
        
        rect.translatesAutoresizingMaskIntoConstraints = false
        rect.bottomAnchor.constraint(equalTo: mainStackView.topAnchor, constant: -Constants.inset100).isActive = true
        rect.widthAnchor.constraint(equalToConstant: Constants.inset100).isActive = true
        rect.heightAnchor.constraint(equalToConstant: Constants.inset100).isActive = true
        rect.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: (UIScreen.main.bounds.width / 2 - Constants.inset50)).isActive = true
        
        label.translatesAutoresizingMaskIntoConstraints = false
        label.topAnchor.constraint(equalTo: rect.bottomAnchor).isActive = true
        label.widthAnchor.constraint(equalToConstant: Constants.inset100 + Constants.inset20).isActive = true
        label.heightAnchor.constraint(equalToConstant: Constants.inset100).isActive = true
        label.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: (UIScreen.main.bounds.width / 2 - Constants.inset50)).isActive = true
        
        resetButton.translatesAutoresizingMaskIntoConstraints = false
        resetButton.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -Constants.inset10).isActive = true
        resetButton.bottomAnchor.constraint(equalTo: mainStackView.topAnchor, constant: -Constants.inset10).isActive = true
        resetButton.widthAnchor.constraint(greaterThanOrEqualToConstant: Constants.inset100).isActive = true
        resetButton.heightAnchor.constraint(greaterThanOrEqualToConstant: Constants.inset50).isActive = true
        
    }
    
    @objc private func changeColor(sender: UIButton) {
        guard sender.backgroundColor == .gray else { return }
        sender.backgroundColor = rect.backgroundColor
        rect.backgroundColor = Colors.random()
        check()
    }
    
    private func check() {
        let checkArray = [0, 1, 2]
        checkArray.forEach {
            guard buttons[$0*3+0].backgroundColor == buttons[$0*3+1].backgroundColor,
                  buttons[$0*3+1].backgroundColor == buttons[$0*3+2].backgroundColor else {
                guard buttons[$0+0].backgroundColor == buttons[$0+3].backgroundColor,
                      buttons[$0+0].backgroundColor == buttons[$0+6].backgroundColor else { return }
                guard buttons[$0+0].backgroundColor != .gray else { return }
                buttons[$0+0].backgroundColor = .gray
                buttons[$0+3].backgroundColor = .gray
                buttons[$0+6].backgroundColor = .gray
                score += 1
                return
            }
            guard buttons[$0*3+0].backgroundColor != .gray else { return }
            buttons[$0*3+0].backgroundColor = .gray
            buttons[$0*3+1].backgroundColor = .gray
            buttons[$0*3+2].backgroundColor = .gray
            score += 1
        }
    }
    
    @objc private func resetGame() {
        buttons.forEach {
            $0.backgroundColor = .gray
        }
        score = 0
    }
    
    @objc private func help() {
        guard score != 0 else { return }
        score -= 1
        rect.backgroundColor = Colors.random()
    }
}

